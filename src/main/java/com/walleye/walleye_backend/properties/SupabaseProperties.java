package com.walleye.walleye_backend.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "supabase")
@Data
public class SupabaseProperties {
    private String url;
    private String bucket;
    private String serviceKey;
}
