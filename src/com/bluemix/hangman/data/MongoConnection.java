/***********************************************************************************
 *  Copyright 2014 IBM
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
**********************************************************************************/
package com.bluemix.hangman.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bson.types.ObjectId;

import com.bluemix.hangman.model.Category;
import com.bluemix.hangman.model.Word;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoConnection {
	
	private MongoClientURI uri;
	
	public MongoConnection(){
		this.uri = new MongoClientURI("mongodb://yourUsername:yourPassword@yourDomain.mongolab.com:33509/yourDatabase");
	}
	
	public List<Category> getCategories(){
		List<Category> categories = new ArrayList<Category>();
		
		try{
			MongoClient client = new MongoClient(uri);
			DB db = client.getDB(uri.getDatabase());

			DBCollection collection = db.getCollection("category");
			BasicDBObject orderBy = new BasicDBObject("name", 1);

			DBCursor categoryCollection = collection.find().sort(orderBy);
			
			while(categoryCollection.hasNext()){
				DBObject contact = categoryCollection.next();
				categories.add(new Category((String)contact.get("_id"),(String)contact.get("name")));
			}
        
			client.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return categories;
	}
		
	public List<Word> getWords(){
		List<Word> words = new ArrayList<Word>();
		
		try{
			MongoClient client = new MongoClient(uri);
			DB db = client.getDB(uri.getDatabase());

			DBCollection collection = db.getCollection("word");
			BasicDBObject orderBy = new BasicDBObject("name", 1);

			DBCursor wordCollection = collection.find().sort(orderBy);
			

			while(wordCollection.hasNext()){
				DBObject word = wordCollection.next();
				words.add(new Word((ObjectId)word.get("_id"),(String)word.get("name"),(String)word.get("category_id")));
			}
        
			client.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return words;
	}
	
	public List<Word> getWordsByCategory(String category_id){
		List<Word> words = new ArrayList<Word>();
		
		try{
			MongoClient client = new MongoClient(uri);
			DB db = client.getDB(uri.getDatabase());

			DBCollection collection = db.getCollection("word");
			BasicDBObject query = new BasicDBObject("category_id", category_id);
			BasicDBObject orderBy = new BasicDBObject("name", 1);

			DBCursor wordCollection = collection.find(query).sort(orderBy);

			while(wordCollection.hasNext()){
				DBObject word = wordCollection.next();
				words.add(new Word((ObjectId)word.get("_id"),(String)word.get("name"),(String)word.get("category_id")));
			}
        
			client.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return words;
	}
	
	public Word getRandomWordByCategory(String category_id){
		Word wordResult = null;
		
		try{
			MongoClient client = new MongoClient(uri);
			DB db = client.getDB(uri.getDatabase());

			DBCollection collection = db.getCollection("word");
			BasicDBObject query = new BasicDBObject("category_id", category_id);

			Long wordCollectionSizeLong = collection.count(query);
			int wordCollectionSize = wordCollectionSizeLong.intValue();
			
			Random generator = new Random();
			
			if(wordCollectionSize>0){
				int random = generator.nextInt(wordCollectionSize);
		      
				DBCursor wordCollection = collection.find(query).limit(1).skip(random);

				if(wordCollection.hasNext()){
					DBObject word = wordCollection.next();
					wordResult = new Word((ObjectId)word.get("_id"),(String)word.get("name"),(String)word.get("category_id"));
				}
			}

			client.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return wordResult;
	}

}
