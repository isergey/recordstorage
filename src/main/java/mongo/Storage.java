package mongo;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Storage {

    private final String dbName = "recordstorage";
    private final String collectionName = "records";
    private MongoClient mongoClient;
    private DBCollection collection;

    public Storage() throws UnknownHostException {
        mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB(dbName);
        if (!db.collectionExists(collectionName)) {
            db.createCollection(collectionName, null);
        }
        collection = db.getCollection(collectionName);
    }

    public void createOrUpdate(List<Record> records) {

        Set<String> recordIds = new HashSet<>();
        for (Record record : records) {
            recordIds.add(record.getId());
        }

        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder = queryBuilder.put("id").in(recordIds);
        BasicDBObject fields = new BasicDBObject();
        fields.put("id", 1);
        fields.put("hash", 1);

        DBCursor cursor = collection.find(queryBuilder.get(), fields);
        cursor.batchSize(100);
        Map<String, DBObject> exsistDbRecords = new HashMap<>();

        while (cursor.hasNext()) {
            DBObject dbRecord = cursor.next();
            exsistDbRecords.put(dbRecord.get("id").toString(), dbRecord);
        }

        Set<Record> creatableRecords = new HashSet<>(records.size());
        Set<Record> updatableRecords = new HashSet<>(records.size());
        for (Record record: records) {
            if (exsistDbRecords.containsKey(record.getId())) {
                DBObject existRecord = exsistDbRecords.get(record.getId());
                if (!existRecord.get("hash").toString().equals(record.getHash())) {
                    updatableRecords.add(record);
                }
            } else {
                creatableRecords.add(record);
            }
        }


        List<DBObject> dbRecords = new ArrayList<>(creatableRecords.size());
        for (Record record : creatableRecords) {
            dbRecords.add(toBasicDBObject(record));
        }

        if (dbRecords.size() > 0) {
            collection.insert(dbRecords, WriteConcern.FSYNC_SAFE);
        }

        if (updatableRecords.size() > 0) {
            for (Record record: updatableRecords) {
                DBObject updateDbRecord = new BasicDBObject();
                updateDbRecord.put("content", record.getContent());
                updateDbRecord.put("hash", record.getHash());
                updateDbRecord.put("updateDate", record.getUpdateDate());
                BasicDBObject query = new BasicDBObject();
                query.put("id", record.getId());
                collection.update(query, new BasicDBObject("$set", updateDbRecord), false, false, WriteConcern.FSYNC_SAFE);
            }
        }
        System.out.println(collection.count(queryBuilder.get()));
    }

    public void delete() {

    }

    public void clearStorage() {
        mongoClient.dropDatabase(dbName);
    }


    private static DBObject toBasicDBObject(Record record) {
        DBObject dbRecord = new BasicDBObject("id", record.getId()).
                append("originalId", record.getOriginalId()).
                append("source", record.getSource()).
                append("format", record.getFormat()).
                append("schema", record.getSchema()).
                append("content", record.getContent()).
                append("createDate", record.getCreateDate()).
                append("updateDate", record.getUpdateDate()).
                append("duplicateKey", record.getDuplicateKey()).
                append("hash", record.getHash()).
                append("deleted", record.isDeleted());
        return dbRecord;
    }
}