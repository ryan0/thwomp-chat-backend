package dev.rmiller.thwompchatbackend;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "thwomp")
public class ThwompConfig {

    private boolean genTestRows;

    public boolean isGenTestRows() {
        return genTestRows;
    }

    public void setGenTestRows(boolean genTestRows) {
        this.genTestRows = genTestRows;
    }
}
