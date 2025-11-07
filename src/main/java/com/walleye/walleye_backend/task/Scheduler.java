package com.walleye.walleye_backend.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.walleye.walleye_backend.services.DeviceService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Scheduler {

    private DeviceService deviceService;
    
    @Scheduled(fixedRate = 120000)
    private void removerExpirados() {
        deviceService.removerDispositivosNaoPareadosExpirados();
    }
}
