# 🚀 WallEye Backend

> Backend da plataforma WallEye responsável pela autenticação de usuários, gerenciamento de dispositivos, pareamento via QR Code e recebimento de alertas de detecção de rachaduras.

---

## 📖 Sobre o Projeto

O WallEye é uma solução de monitoramento estrutural baseada em visão computacional e sistemas embarcados.

O sistema permite que dispositivos instalados em estruturas sejam vinculados a usuários através de um processo de pareamento utilizando QR Code. Após o pareamento, os dispositivos podem enviar alertas de detecção para a plataforma, permitindo o acompanhamento em tempo real através da aplicação web.

---

## ✨ Funcionalidades

### 🔐 Autenticação

* Cadastro de usuários
* Login com JWT
* Controle de acesso baseado em autenticação

### 📟 Gerenciamento de Dispositivos

* Cadastro de dispositivos
* Geração de código de pareamento
* Expiração automática do código de pareamento
* Associação entre dispositivo e usuário

### 📱 Pareamento

* Geração de QR Code contendo:

  * ID do dispositivo
  * Código de pareamento
  * URL da API

* Pareamento realizado diretamente pelo dispositivo embarcado

### 🚨 Alertas

* Registro de alertas enviados pelos dispositivos
* Vinculação automática do alerta ao dispositivo responsável
* Relacionamento entre:

  * Usuário
  * Dispositivo
  * Alerta

### ⏱️ Manutenção Automática

* Remoção de dispositivos não pareados após expiração do código
* Tarefas agendadas utilizando Spring Scheduler

---

## 🏗️ Arquitetura

```text
┌─────────────┐
│   Angular   │
│ Front-End   │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│ Spring Boot │
│   Backend   │
└──────┬──────┘
       │
 ┌─────┴─────┐
 ▼           ▼
PostgreSQL   Raspberry Pi
             (WallEye Device)
                   │
                   ▼
          OpenCV + IA
          Detecção de Rachaduras
```

---

## 🛠️ Tecnologias Utilizadas

### Backend

* Java 21
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA
* Hibernate
* Lombok
* Maven

### Banco de Dados

* PostgreSQL

### Integração IoT

* Raspberry Pi
* Python
* OpenCV
* QR Code

---

## 📂 Estrutura do Projeto

```text
src/
├── config/
│   ├── security/
│   └── scheduler/
│
├── controllers/
│   ├── AuthController
│   ├── DeviceController
│   └── AlertController
│
├── dto/
│   ├── auth/
│   ├── device/
│   └── alert/
│
├── entities/
│   ├── Usuario
│   ├── Dispositivo
│   └── Alerta
│
├── repositories/
│   ├── UsuarioRepository
│   ├── DispositivoRepository
│   └── AlertaRepository
│
├── services/
│   ├── AuthService
│   ├── DeviceService
│   └── AlertService
│
└── utils/
```

---

## 📲 Fluxo de Pareamento

### 1. Cadastro do dispositivo

O usuário registra um novo dispositivo pela aplicação web.

### 2. Geração do QR Code

```json
{
  "id": "c96410e7-284d-4d90-93cb-ec7e8f4eb651",
  "codigo_pareador": "D2JEHO",
  "api_url": "http://localhost:8080"
}
```

### 3. Leitura pelo dispositivo

O Raspberry Pi lê o QR Code e extrai as informações.

### 4. Solicitação de pareamento

```json
{
  "id": "c96410e7-284d-4d90-93cb-ec7e8f4eb651",
  "codigo_pareador": "D2JEHO"
}
```

### 5. Validação

O backend verifica:

* Existência do dispositivo
* Código de pareamento
* Expiração do código
* Estado de pareamento

### 6. Vinculação

O dispositivo é marcado como pareado e passa a operar normalmente.

---

## 🚨 Fluxo de Alertas

### Envio pelo dispositivo

```json
{
  "id_dispositivo": "c96410e7-284d-4d90-93cb-ec7e8f4eb651",
  "nivel": "CRITICO",
  "mensagem": "Rachadura detectada"
}
```

### Processamento

O backend:

1. Localiza o dispositivo
2. Cria o alerta
3. Associa o alerta ao dispositivo
4. Disponibiliza os dados para o dashboard

---

## 🔒 Segurança

### Usuários

* JWT Authentication
* Senhas criptografadas com BCrypt

### Dispositivos

* Pareamento baseado em código temporário
* Expiração automática de códigos
* Associação exclusiva entre usuário e dispositivo

---

## ▶️ Executando o Projeto

### Clonar o repositório

```bash
git clone <url-do-repositorio>
```

### Configurar variáveis de ambiente

```properties
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=

jwt.secret=
```

### Executar

```bash
mvn spring-boot:run
```

---

## 📌 Próximas Funcionalidades

* Notificações em tempo real
* Dashboard analítico
* Histórico de alertas
* Upload de imagens das detecções
* Classificação automática de severidade
* Integração com WebSocket
* Monitoramento de status dos dispositivos

---

## 👨‍💻 Autor

**Caio Viterbo**

Desenvolvedor de Software focado em desenvolvimento Full Stack e com interesse em sistemas embarcados, visão computacional e inteligência artificial.

---

## 🌟 WallEye

> Transformando monitoramento estrutural em dados acionáveis através de Inteligência Artificial e Sistemas Embarcados.
