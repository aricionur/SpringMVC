package com.aricionur.model;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;


import com.mongodb.DBCursor;


public class MongoDAO {

	MongoClient mongo;
	DB db;
	DBCollection collection;
	Gson gson ;
//	private static List<Person> allPersonList;
	private static int currentId;
	private static MongoDAO uniqueInstance; 
		
	public static synchronized MongoDAO getInstance() {
		
		if(uniqueInstance == null) {
			uniqueInstance = new MongoDAO();
		}
		return uniqueInstance;
	}
	
	private MongoDAO() {
		System.out.println("***********Mongo DAO constructer calıstırıldı********");
		// Since 2.10.0, uses MongoClient
		try {
			this.mongo = new MongoClient("localhost", 27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/**** Get database ****/
		// if database doesn't exists, MongoDB will create it for you
		this.db = mongo.getDB("persondb");

		/**** Get collection / table from 'testdb' ****/
		// if collection doesn't exists, MongoDB will create it for you
		this.collection = db.getCollection("person");
		
		this.collection.remove(new BasicDBObject());
		
		this.gson = new Gson();
//		this.allPersonList = new ArrayList<>();
		int currentId=1;
		BasicDBObject dbObject_initial = new BasicDBObject();
		dbObject_initial.put("id", currentId);
		dbObject_initial.put("name", "NameOne");
		dbObject_initial.put("surname", "SurnameOne");
		dbObject_initial.put("phoneNumber", "5351112233");
		this.collection.insert(dbObject_initial);
		
		BasicDBObject dbObject_initial_2 = new BasicDBObject();
		dbObject_initial_2.put("id", ++currentId);
		dbObject_initial_2.put("name", "NameTwo");
		dbObject_initial_2.put("surname", "SurnameTwo");
		dbObject_initial_2.put("phoneNumber", "5352223344");
		this.collection.insert(dbObject_initial_2);
	}

	public Person convertDBObjectToPerson(BasicDBObject dbObject)  {
		Person person = gson.fromJson(dbObject.toString(), Person.class);
		return person;
	}
	
	public Person insert(Person person) {
		/**** Insert ****/
		// create a document to store key and value
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("id", ++currentId );
		dbObject.put("name", person.getName());
		dbObject.put("surname", person.getSurname());
		dbObject.put("phoneNumber", person.getPhoneNumber());
		
		collection.insert(dbObject);
		
		return convertDBObjectToPerson(dbObject);
		
		
	}

	public void update(Person person) {
		System.out.println("\n********mongo db içi update***********");
		System.out.println("mongo ici update edilen person : " + person.getId() +" "+ person.getName() +" "+ person.getSurname() +" "+ person.getPhoneNumber() );
	
		BasicDBObject searchQuery = new BasicDBObject().append("id", person.getId());
		
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("id", person.getId());
		dbObject.put("name", person.getName());
		dbObject.put("surname", person.getSurname());
		dbObject.put("phoneNumber", person.getPhoneNumber());
		
		collection.update(searchQuery, dbObject);	
//		BasicDBObject searchQuery = new BasicDBObject().append("id", person.getId());
		
		
		
	}
	
	public void delete(int id) {
		System.out.println("\n********mongo db içi delete***********");
		System.out.println("mongo ici DELETE edilen person id si : " + id );
	
		BasicDBObject searchQuery = new BasicDBObject().append("id", id);
		
//		BasicDBObject dbObject = new BasicDBObject();
//		dbObject.put("id", person.getId());
//		dbObject.put("name", person.getName());
//		dbObject.put("age", person.getAge());
		collection.remove(searchQuery);
//		collection.update(searchQuery, dbObject);	
		
		
	}
	
	public List<BasicDBObject> getMongoDBAsBasicDBObject(){
		
		List<BasicDBObject> basicDBObjectList = new ArrayList<>();

		DBCursor cursorDoc = collection.find();
		
		while (cursorDoc.hasNext()) {
			BasicDBObject dbObject =(BasicDBObject) cursorDoc.next();
			basicDBObjectList.add(dbObject); 
		}
		
		return basicDBObjectList;
		
	}
	
	public List<Person> getMongodbAsPersonList() {
		List<Person> PersonList = new ArrayList<>();
		List<BasicDBObject> dbObjectList = getMongoDBAsBasicDBObject();

		for (BasicDBObject eachBasicDBObject : dbObjectList) {
			Person person = gson.fromJson(eachBasicDBObject.toString(), Person.class);
			PersonList.add(person);
		}
	
		return PersonList;
	}
	
	public void displayMongoDB() {
		List<Person> personList = getMongodbAsPersonList();
		
		System.out.println("\n   ******  writing person list from current mongo db  ****** from displayMongoDB() method");
		for (Person eachPerson : personList) {
			System.out.println("Person info : " + eachPerson.getId() + " " +eachPerson.getName() + " " + eachPerson.getSurname() +" " + eachPerson.getPhoneNumber());
		}	
	}
	
	

}
