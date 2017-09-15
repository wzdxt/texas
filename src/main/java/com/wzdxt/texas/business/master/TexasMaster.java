package com.wzdxt.texas.business.master;

import com.wzdxt.texas.model.Card;

import java.util.Collection;

/**
 * Created by wzdxt on 2017/9/3.
 */
public interface TexasMaster {
    MasterDecision suggest(Collection<Card> my, Collection<Card> common);
}
