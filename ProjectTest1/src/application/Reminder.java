package application;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.types.ObjectId;

public class Reminder {
	static String NotifTitle; static String NotifDate; static String NotifLocation; static String NotifComments; static String UserID;
	//create notification object
	public Reminder(String name, String date, String location, String comments, String id) {		
		NotifTitle = name;
		NotifDate = date;
		NotifLocation = location;
		NotifComments = comments;
		UserID = id;
		}
	
	public static void main() {
		//creating connection to Mongo
		MongoClientURI uri = new MongoClientURI(
			    "mongodb+srv://notifiedDB:1yNObuHirguaJytk@notified.0jtvj.mongodb.net/Notified?retryWrites=true&w=majority");
		
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("Notified");
		
		// create a collection
		//database.createCollection("Notifications");
		
		// create document
		Document document = new Document();
		//Reminder myReminder = new Reminder(NotifTitle, NotifDate, NotifLocation, NotifComments);
		document.append("UserID", UserID);
		document.append("EventName", NotifTitle);
		document.append("Date", NotifDate);
		document.append("Location", NotifLocation);
		document.append("Comments", NotifComments);
		
		//insert document into collection
		Document found = database.getCollection("Notifications").find(document).first();

		if (found == null) {
		    database.getCollection("Notifications").insertOne(document);
		}
	}
}
