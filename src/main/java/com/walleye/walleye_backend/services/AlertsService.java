package com.walleye.walleye_backend.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.walleye.walleye_backend.dto.AlertsReceiveDto;
import com.walleye.walleye_backend.entities.Alerta;
import com.walleye.walleye_backend.entities.Dispositivo;
import com.walleye.walleye_backend.properties.SupabaseProperties;
import com.walleye.walleye_backend.repositories.AlertaRepository;
import com.walleye.walleye_backend.repositories.DispositivoRepository;

import jakarta.annotation.Nullable;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AlertsService {
    
    private final AlertaRepository alertaRepository;

    private final DispositivoRepository dispositivoRepository;

    private final EmailService emailService;

    private final WebClient http = WebClient.builder().build();
    
    private final SupabaseProperties supabaseProperties;

    public Alerta createAlerts(AlertsReceiveDto dto, @Nullable MultipartFile file) {
        Dispositivo dispositivo = dispositivoRepository.findById(dto.getId_dispositivo())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dispositivo não encontrado"));
        Alerta alerta = new Alerta();
        alerta.setData_deteccao(dto.getDate_detection());
        alerta.setSeveridade(dto.getSeverity());
        alerta.setMensagem(dto.getMessage());
        alerta.setDispositivo(dispositivo);
        alerta.setResolvido(false);
        alerta.setData_deteccao(LocalDateTime.now());
        dispositivo.setUltima_atualizacao(LocalDateTime.now());

          if (file != null && !file.isEmpty()) {
            String userId = dispositivo.getUsuario().getId().toString();
            String objectPath = buildObjectPath(userId, dto.getId_dispositivo(), file.getContentType());
            uploadToSupabase(supabaseProperties.getBucket(), objectPath, file);
            String signedUrl = createSignedUrl(supabaseProperties.getBucket(), objectPath, 3600);
            alerta.setUrl_imagem(signedUrl);
        }

        try {
           if (dto.getSeverity().equalsIgnoreCase("alta") || dto.getSeverity().equalsIgnoreCase("crítica")) {
            emailService.sendEmail(dispositivo.getUsuario(), dispositivo, alerta);
        }
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return alertaRepository.save(alerta);
    }

    
     private String buildObjectPath(String userId, UUID deviceId, String contentType) {
        String ts = java.time.OffsetDateTime.now().toString().replace(":", "-");
        String ext = switch (contentType) {
            case "image/png"  -> ".png";
            case "image/jpeg" -> ".jpg";
            default           -> ".jpg";
        };
        return userId + "/" + deviceId + "/" + ts + ext;
    }

    // 🔹 Faz o upload binário pro Supabase Storage
    private void uploadToSupabase(String bucket, String objectPath, MultipartFile file) {
        String url = supabaseProperties.getUrl() + "/storage/v1/object/" + bucket + "/" + objectPath;

        http.post()
           .uri(url)
           .header("Authorization", "Bearer " + supabaseProperties.getServiceKey())
           .header("apikey", supabaseProperties.getServiceKey())
           .header("Content-Type", safeContentType(file))
           .header("Cache-Control", "max-age=31536000, immutable")
           .bodyValue(toBytes(file))
           .retrieve()
           .onStatus(HttpStatusCode::isError, resp ->
               resp.bodyToMono(String.class).map(RuntimeException::new))
           .toBodilessEntity()
           .block();
    }

    // 🔹 Gera link temporário de acesso
    private String createSignedUrl(String bucket, String objectPath, int expiresInSeconds) {
        String url = supabaseProperties.getUrl() + "/storage/v1/object/sign/" + bucket + "/" + objectPath;
        Map<String, Object> body = Map.of("expiresIn", expiresInSeconds);

        return http.post()
           .uri(url)
           .header("Authorization", "Bearer " + supabaseProperties.getServiceKey())
           .header("apikey", supabaseProperties.getServiceKey())
           .contentType(MediaType.APPLICATION_JSON)
           .bodyValue(body)
           .retrieve()
           .bodyToMono(Map.class)
           .map(m -> (String) m.get("signedURL"))
           .block();
    }

    // Utilitários
    private byte[] toBytes(MultipartFile file) {
        try { return file.getBytes(); }
        catch (Exception e) { throw new RuntimeException("Falha ao ler arquivo", e); }
    }

    private String safeContentType(MultipartFile file) {
        String ct = file.getContentType();
        return (ct != null && ct.startsWith("image/")) ? ct : "image/jpeg";
    }
}
