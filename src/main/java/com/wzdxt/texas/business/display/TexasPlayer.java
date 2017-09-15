package com.wzdxt.texas.business.display;

import com.wzdxt.texas.business.master.TexasMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dai_x on 17-9-15.
 */
@Component
public class TexasPlayer {
    @Autowired
    private TexasMaster master;

    public void actByStatus(GameStatus status) {

    }
}
