package com.wzdxt.texas.business.display;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Created by dai_x on 17-9-5.
 */
@Data
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@Component
public class ScreenParam {
    int width, height;
    int gameX1, gameY1, gameX2, gameY2;
    double rate;
}
