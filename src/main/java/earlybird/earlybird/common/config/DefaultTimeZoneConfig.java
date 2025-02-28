package earlybird.earlybird.common.config;

import jakarta.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class DefaultTimeZoneConfig {

    @PostConstruct
    public void setDefaultTimeZoneToKST() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
