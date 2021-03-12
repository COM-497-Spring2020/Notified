package application;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;

public class DatabaseConnection {

	public static void main(String[] args) {
		//MongoClientURI uri = new MongoClientURI(
			    //"mongodb+srv://notifiedDB:1yNObuHirguaJytk@notified.0jtvj.mongodb.net/test?retryWrites=true&w=majority");
		MongoClientURI uri = new MongoClientURI(
			    "mongodb+srv://user_1:passworduser@cluster0.99qkz.mongodb.net/Test?retryWrites=true&w=majority");
		try(MongoClient mongoClient = new MongoClient(uri))
			{
				MongoDatabase database = mongoClient.getDatabase("Test");
				MongoCollection<Document> collection = database.getCollection("Test");
				Document query = new Document("_id", new ObjectId("60491554e4bbd92f850761c5"));
				Document result = collection.find(query).iterator().next();
//				Document document = new Document();
//				document.append("title", document);
//				document.append("date", document);
//				document.append("location", document);
//				document.append("comments", document);
				
				System.out.println("Test3ddddd: " + result.getString("Hi"));
			}
	}

}
