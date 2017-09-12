package com.wzdxt.texas.business.display;

import java.util.concurrent.Future;

/**
 * Created by dai_x on 17-9-4.
 */
public interface Displayer {
    ScreenParam matchAnchor();
    Future<ScreenParam> matchAnchorAsync();
    GameStatus getCurrentStatus();
}
