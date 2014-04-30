package mongo;

import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.List;

public class Storage {
    private MongoClient mongoClient;

    public Storage() throws UnknownHostException {
        mongoClient = new MongoClient( "localhost" , 27017 );
    }

    public void createOrUpdate(List<Record> records) {

    }

    public void delete() {

    }
}
