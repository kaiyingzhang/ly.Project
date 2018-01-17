package ly.keinproj.demo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import static com.mongodb.client.model.Filters.eq;
@RestController
public class MongoDBJDBC {
    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> collection;
    JSONArray jsonArray = new JSONArray();

    String originalURL;
    String shorterURL = "";

    //connect to database
    public void connect() throws IOException, InterruptedException{
        // Creating a Mongo client
        MongoClient mongo = new MongoClient( "localhost" , 27017 );

        database = mongoClient.getDatabase("urlDB");
        collection = database.getCollection("test") ;

        System.out.println("Connecting database...");
        System.out.println(collection.count()+ " urls in database");

    }

    //insert new url data
    public void insertData(){

    }

    public String runAll() throws IOException, InterruptedException{
        connect();
        return shorterURL;

    }

    public String setupURL(String currentURL){
        long countOfCurrentURL = collection.count();
        //long countOfCurrentURL;
        String shortURL = "";

            if(countOfCurrentURL<=26){
                String s = "";
                s = (char) ('a' + countOfCurrentURL -1) + s;
                return "00000"+s;
            }
            if(countOfCurrentURL>26 && countOfCurrentURL<=52){

            String s = "";
            s = (char) ('A' + countOfCurrentURL -27) + s;
            return "00000"+s;
            }else{
                String s = "Z";
                String str = String.format("%5d", countOfCurrentURL-53).replace(" ", "0");
                return str+s;
            }

    }

    public void letsGo() throws IOException, InterruptedException {

        String longURL = "http://mongodb.github.io/masdfasdfafdafdafdadfab";

        MongoClient mongoClient = new MongoClient("localhost", 27017);

        database = mongoClient.getDatabase("urlDB");
        collection = database.getCollection("tests");

        System.out.println("Connecting database...");
        System.out.println(collection.count() + " urls in database");


        Document testDoc = collection.find(eq("longURL", longURL)).first();

        if (testDoc!=null) {
            System.out.println("URL already exists...");
            JSONObject jsonObj = new JSONObject(testDoc.toJson());
            System.out.println("shortURL is :" + jsonObj.get("shortURL"));
            shorterURL = jsonObj.get("shortURL").toString();

        } else {

            //create a new short URL and insert into collection
            String shortURL = setupURL(longURL);
            Document doc = new Document("longURL", longURL).append("shortURL", shortURL);
            collection.insertOne(doc);

            //Document myDoc = collection.find().first();
            //System.out.println(myDoc.toJson());

            //find a specific collection
            Document myDoc = collection.find(eq("longURL", longURL)).first();

            if (myDoc != null) System.out.println("result : " + myDoc.toJson());

            JSONObject jsonObj = new JSONObject(myDoc.toJson());
            System.out.println("shortURL is :" + jsonObj.get("shortURL"));
            shorterURL = jsonObj.get("shortURL").toString();


            //check and delete a specific document
        /*
        DeleteResult deleteResult = collection.deleteMany(gte("longURL", "http://mongodb.github.io/mongo-java-driver/3.4/driver/getting-started/quick-start/"));
        System.out.println("delete Count:"+deleteResult.getDeletedCount());
        */


            //print all document in a collection
        /*
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            System.out.println("All documents:");
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }*/


        }
    }

    @RequestMapping("/kein")
    public ModelAndView index() {
        return new ModelAndView("/shorURL.html");

    }

    @RequestMapping("/keinnie")
    public static void main(String[] args) throws IOException, InterruptedException{
            MongoDBJDBC ss = new MongoDBJDBC();
            ss.letsGo();

    }

}