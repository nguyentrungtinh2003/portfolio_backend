package com.TrungTinhBackend.portfolio_backend.Config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    private String cloudName = "dzphikbho";

    private String apiKey = "851956915323885";

    private String apiSecret = "EQXXjC4wJooQilcvshHwBmC20Bg";

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }
}
