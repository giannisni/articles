package com.database.articles.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="feature")
public class FeatureProperties {

    private boolean fulltextSearchEnabled;

    public boolean isFulltextSearchEnabled() {
        return fulltextSearchEnabled;
    }

    public void setFulltextSearchEnabled(boolean fulltextSearchEnabled) {
        this.fulltextSearchEnabled = fulltextSearchEnabled;
    }
}
