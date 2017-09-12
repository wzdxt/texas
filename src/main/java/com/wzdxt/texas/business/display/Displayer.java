package com.wzdxt.texas.business.display;

/**
 * Created by dai_x on 17-9-4.
 */
public interface Displayer {
    ScreenParam matchAnchor();
    void matchAnchorAsync();
    GameStatus getCurrentStatus();
}
