package app;

import mongo.Record;
import mongo.Storage;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sergey on 30.04.14.
 */
public class Main {
    public static void main(String[] args) {
        Storage storage = null;
        try {
            storage = new Storage();
            //storage.clearStorage();
            storage.createOrUpdate(getRecords());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    private static List<Record> getRecords() {
        List<Record> records = new ArrayList<>();
        Date now = new Date();
        for (int i = 0; i < 100; i++) {
            Record record = new Record(String.valueOf(i));
            record.setContent("1234567");
            record.setOriginalId(i + "orig");
            record.setCreateDate(now);
            record.setUpdateDate(now);
            record.setFormat("iso2709");
            record.setSchema("rusmarc");
            record.setSource("unilib");
            record.setDuplicateKey(i + "dup");
            records.add(record);
        }
        return records;
    }
}
