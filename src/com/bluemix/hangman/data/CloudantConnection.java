package com.bluemix.hangman.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;

import com.bluemix.hangman.model.Category;
import com.bluemix.hangman.model.CategoryRepository;
import com.bluemix.hangman.model.Word;
import com.bluemix.hangman.model.WordRepository;

public class CloudantConnection {
	
	HttpClient httpClient;
	
	public CloudantConnection(){		
		try{
			JSONObject obj = new JSONObject(System.getenv("VCAP_SERVICES"));
			String[] names = JSONObject.getNames(obj);
		
			if (names != null) {
				for (String name : names) {
					if (name.equals("cloudantNoSQLDB")) {
						JSONArray val = obj.getJSONArray(name);
						JSONObject serviceAttr = val.getJSONObject(0);
						JSONObject credentials = serviceAttr.getJSONObject("credentials");
						httpClient = new StdHttpClient.Builder()
						.url(credentials.getString("url"))
						.build();
						break;
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public List<Category> getCategories(){
		CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
		CouchDbConnector db = new StdCouchDbConnector("category", dbInstance);
		CategoryRepository categoryRepo = new CategoryRepository(db);
		return categoryRepo.getAll();
	}
		
	public List<Word> getWords(){
		CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
		CouchDbConnector db = new StdCouchDbConnector("word", dbInstance);
		WordRepository wordRepo = new WordRepository(db);
		return wordRepo.getAll();
	}
	
	public List<Word> getWordsByCategory(String category_id){
		List<Word> words = this.getWords();
		List <Word> wordsByCategory = new ArrayList<Word>();
		for(int index=0; index<words.size(); index++){
			if(words.get(index).getCategory_id().equals(category_id)){
				wordsByCategory.add(words.get(index));
			}
		}
		return wordsByCategory;
	}
	
	public Word getRandomWordByCategory(String category_id){
		Word word = null;
		List<Word> words = this.getWordsByCategory(category_id);
		Random generator = new Random();
		if(words.size()>0){
			int random = generator.nextInt(words.size());
			word = words.get(random);
		}
		return word;
	}

}
