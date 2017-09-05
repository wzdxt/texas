package com.wzdxt.texas.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by dai_x on 17-9-5.
 */
@Data
@Component
@ConfigurationProperties("displayer")
public class DisplayerConfigure {
    Anchor anchor;

    @Data
    public static class Anchor {
        int fix;
        int delay;
        int width;
        int height;
        double mistake;
    }
}
