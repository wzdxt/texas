package com.wzdxt.texas.service;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.util.ByteUtil;
import lombok.extern.slf4j.Slf4j;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by dai_x on 17-9-25.
 */
@Slf4j
@Component
public class LevelDB {
    private DB db75;
    private DB db23;

    public LevelDB() {
        Options options = new Options().createIfMissing(true);
        try {
            db75 = new Iq80DBFactory().open(new File(Constants.LEVELDB_75_DIR), options);
            db23 = new Iq80DBFactory().open(new File(Constants.LEVELDB_23_DIR), options);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * convert 7 cards set to 5 cards hand
     * @param set
     * @return
     */
    public Long get7to5(long set) {
        byte[] bytes = db75.get(ByteUtil.build(set));
        if (bytes == null) return null;
        return ByteUtil.parseToLong(bytes);
    }

    /**
     * convert 2 my cards and 3-5 common cards to Double List
     * @param my
     * @param common
     * @return
     */
    public List<Double> get23toDouble(int my, int common) {
        byte[] bytes = db23.get(ByteUtil.build(my, common));
        if (bytes == null) return null;
        List<Double> list = ByteUtil.parseToDoubleList(bytes);
        return list;
    }

    public void put7to5(long set, long hand) {
        db75.put(ByteUtil.build(set), ByteUtil.build(hand));
    }

    public void put23toDouble(int my, int common, List<Double> list) {
        db23.put(ByteUtil.build(my, common), ByteUtil.build(list));
    }

    public void finalize() throws IOException {
        db75.close();
        db23.close();
    }
}

