package application;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.types.ObjectId;

public class Reminder {
	static String NotifTitle; static String NotifDate; static String NotifLocation; static String NotifComments; static String UserID; 
		static String StartTime; static String EndTime; static String Alert; static String Repeat; static String ReminderID;
	//create notification object
	public Reminder(String name, String date, String location, String comments, String id, 
			String start, String end, String alert, String repeat, String reminderID) {		
		NotifTitle = name;
		NotifDate = date;
		NotifLocation = location;
		NotifComments = comments;
		UserID = id;
		StartTime = start;
		EndTime = end;
		Alert = alert;
		Repeat = repeat;
		ReminderID = reminderID;
		}
	
	public static void main() {
		//creating connection to Mongo
		MongoClientURI uri = new MongoClientURI(
			    "mongodb+srv://notifiedDB:1yNObuHirguaJytk@notified.0jtvj.mongodb.net/Notified?retryWrites=true&w=majority");
		
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("Notified");
		
		// create document
		Document document = new Document();
		document.append("UserID", UserID);
		document.append("ReminderID", ReminderID);
		document.append("EventName", NotifTitle);
		document.append("Date", NotifDate);
		document.append("Location", NotifLocation);
		document.append("Comments", NotifComments);
		document.append("StartTime", StartTime);
		document.append("EndTime", EndTime);
		document.append("Alert", Alert);
		document.append("Repeat", Repeat);
		
		//insert document into collection
		Document found = database.getCollection("Notifications").find(document).first();

		if (found == null) {
		    database.getCollection("Notifications").insertOne(document);
		}
	}
}
