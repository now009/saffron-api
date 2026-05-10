package com.saffron.qbank.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "qbank")
public class QbankProperties {

    private String imageDir = "C:/qbank/images";
    private String imagePath = "/qbank-images";

    public String getImageDir() { return imageDir; }
    public void setImageDir(String imageDir) { this.imageDir = imageDir; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
