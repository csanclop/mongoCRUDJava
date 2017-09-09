package com.mongo;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoCRUD {

	public static void main(String[] args) throws UnknownHostException {

		// Connect to MongoDB server
		MongoClient serverConnection = new MongoClient("localhost", 27017);

		// Create a connection to lotus database
		DB db = serverConnection.getDB("prova");

		// Display all the databases
		List<String> dbNames = serverConnection.getDatabaseNames();
		for (String databaseName : dbNames) {
			System.out.println(databaseName);
		}

		// Creating a non capped collection
		DBObject options = BasicDBObjectBuilder.start().add("capped", false).add("size", 4194304).get();
		// db.createCollection("lotusCollection", options );

		// Inserting a document into the collection
		DBObject firstObject = BasicDBObjectBuilder.start().add("firstName", "christian").append("lastName", "lopez")
				.get();
		db.getCollection("provaCollection").insert(firstObject);

		// Search a document
		BasicDBObject searchObject = new BasicDBObject();
		searchObject.put("firstName", "christian");
		DBCursor searchResults = db.getCollection("provaCollection").find(searchObject);
		while (searchResults.hasNext()) {
			System.out.println("Before Update--->" + searchResults.next());
		}

		// Update a document
		BasicDBObject searchObjectNew = new BasicDBObject();
		searchObjectNew.put("firstName", "christian");

		DBObject newDocument = BasicDBObjectBuilder.start().add("firstName", "christian")
				.append("middleName", "sanchez").append("lastName", "lopez").get();
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$set", newDocument);
		db.getCollection("provaCollection").updateMulti(searchObjectNew, updateObject);

		searchResults = db.getCollection("provaCollection").find(searchObjectNew);
		while (searchResults.hasNext()) {
			System.out.println("After Update -->" + searchResults.next());
		}
		// will remove all the documents matching search criteria
		db.getCollection("provaCollection").remove(searchObjectNew);
	}

}
