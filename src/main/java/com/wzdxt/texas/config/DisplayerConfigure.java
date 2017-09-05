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
public class DisplayerConfigure {
    @Value("${displayer.anchor.fix}")
    int anchorFix;
    @Value("${displayer.anchor.delay}")
    int anchorDelay;
}
