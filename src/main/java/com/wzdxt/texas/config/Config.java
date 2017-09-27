package com.wzdxt.texas.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by dai_x on 17-9-27.
 */
@EnableConfigurationProperties
@EnableAsync
@ComponentScan(basePackages = {"com.wzdxt.texas"})
//@EnableCaching
public class Config {
}
