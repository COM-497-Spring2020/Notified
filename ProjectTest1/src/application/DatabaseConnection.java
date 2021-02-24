package application;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;

public class DatabaseConnection {

	public static void main(String[] args) {
		MongoClientURI uri = new MongoClientURI(
			    "mongodb+srv://notifiedDB:1yNObuHirguaJytk@notified.0jtvj.mongodb.net/test?retryWrites=true&w=majority");
		
		try(MongoClient mongoClient = new MongoClient(uri))
			{
				MongoDatabase database = mongoClient.getDatabase("test");
				MongoCollection<Document> collection = database.getCollection("test");
				Document query = new Document("_id", new ObjectId("602eca2daa940e8b3e18973e"));
				Document result = collection.find(query).iterator().next();
//				Document document = new Document();
//				document.append("title", document);
//				document.append("date", document);
//				document.append("location", document);
//				document.append("comments", document);
				
				System.out.println("Test3: " + result.getString("test3"));
			}
	}

}
