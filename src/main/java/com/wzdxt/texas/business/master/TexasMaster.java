package com.wzdxt.texas.business.master;

import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CommonCard;
import com.wzdxt.texas.model.MyCard;

import java.util.Collection;

/**
 * Created by wzdxt on 2017/9/3.
 */
public interface TexasMaster {
    MasterDecision suggest(MyCard my, CommonCard common);
}
