package dev.rmiller.thwompchatbackend;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "thwomp")
public class ThwompConfig {

    private String frontendBaseUrl;
    private boolean genTestRows;

    public String getFrontendBaseUrl() {
        return frontendBaseUrl;
    }

    public void setFrontendBaseUrl(String frontendBaseUrl) {
        this.frontendBaseUrl = frontendBaseUrl;
    }

    public boolean isGenTestRows() {
        return genTestRows;
    }

    public void setGenTestRows(boolean genTestRows) {
        this.genTestRows = genTestRows;
    }
}
