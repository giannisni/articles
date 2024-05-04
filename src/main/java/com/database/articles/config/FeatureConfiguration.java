package com.database.articles.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix="feature")
@Validated
public class FeatureConfiguration {

    private boolean fulltextSearchEnabled;

    public boolean isFulltextSearchEnabled() {
        return fulltextSearchEnabled;
    }

    public void setFulltextSearchEnabled(boolean fulltextSearchEnabled) {
        this.fulltextSearchEnabled = fulltextSearchEnabled;
    }
}
