package com.wzdxt.texas;

import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;
import com.wzdxt.texas.service.LevelDB;
import com.wzdxt.texas.util.ByteUtil;
import lombok.extern.slf4j.Slf4j;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.WriteBatch;
import org.iq80.leveldb.impl.Iq80DBFactory;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.BitSet;

@Slf4j
public class PreProcessor23toDouble {

    public static void main(String[] args) throws Exception {
        // open new db
        Options options = new Options().createIfMissing(true);
        DB db = new Iq80DBFactory().open(new File(Constants.LEVELDB_DIR), options);
        // write an empty batch
        WriteBatch batch = db.createWriteBatch();
        batch.put(ByteUtil.build(111, 222L), ByteUtil.build(333L, 444L));
        batch.close();
        db.write(batch);
        // read
        byte[] bytes = db.get(ByteUtil.build(111, 222L));
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.put(bytes);
        buffer.flip();
        log.info("{} {}", buffer.getLong(), buffer.getLong());
        // close the db
        db.close();
    }

    public void process() {
        BitSet selected = new BitSet(Constants.TOTAL_CARD);
        CardSet mySet = new CardSet();
        // my1
        for (int my1 = 0; my1 < Constants.TOTAL_CARD; my1++) {
            Card myCard1 = Card.of(my1);
            mySet.add(myCard1);
            selected.set(my1);
            // my2
            for (int my2 = my1 + 1; my2 < Constants.TOTAL_CARD; my2++) {
                Card myCard2 = Card.of(my2);
                mySet.add(myCard2);
                selected.set(my2);

                CardSet commonSet = new CardSet();
                // common1
                for (int common1 = 0; common1 < Constants.TOTAL_CARD; common1++) {
                    if (!selected.get(common1)) {
                        Card commonCard1 = Card.of(common1);
                        commonSet.add(commonCard1);
                        selected.set(common1);
                        // common2
                        for (int common2 = common1 + 1; common2 < Constants.TOTAL_CARD; common2++) {
                            if (!selected.get(common2)) {
                                Card commonCard2 = Card.of(common2);
                                commonSet.add(commonCard2);
                                selected.set(common2);
                                // common3
                                for (int common3 = common2 + 1; common3 < Constants.TOTAL_CARD; common3++) {
                                    if (!selected.get(common3)) {
                                        Card commonCard3 = Card.of(common3);
                                        commonSet.add(commonCard3);

                                        // process
                                        process(mySet, commonSet);

                                        commonSet.remove(commonCard3);
                                    }
                                }

                                selected.clear(common2);
                                commonSet.remove(commonCard2);
                            }
                        }

                        selected.clear(common1);
                        commonSet.remove(commonCard1);
                    }
                }

                selected.clear(my2);
                mySet.remove(myCard2);
            }
            selected.clear(my1);
            mySet.remove(myCard1);
        }
    }

    private boolean start = false;
    private LevelDB levelDB;

    public void process(CardSet my, CardSet common) {
        if (!start) {
        }

        if (start) {

        }
    }

}

