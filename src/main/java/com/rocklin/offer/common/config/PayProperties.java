package com.rocklin.offer.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "pay")
public class PayProperties {
    private String url;
    private String pid;
    private String key;
    private String notifyUrl;
    private String returnUrl;
}
