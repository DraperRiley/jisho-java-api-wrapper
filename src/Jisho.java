import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

//TODO: Complete JAVADOC

/**
 * 
 * Encapsulation of the Jisho.org API utilizing Google's GSON library to parse JSON using multiple nested classes. 
 * 
 * @author Riley Draper
 * @version 1.0
 *
 */

public class Jisho {
	
	/**
	 * a variable to hold the JSON data
	 */
	private String jsonString;
	
	/**
	 * the desired word to search. refer to Jisho.org for search methods
	 */
	private String searchWord;
	
	/**
	 * HTTP response status code
	 */
	private int responseCode;
	
	/**
	 * A nested class instance which will store the JSON data into individual entries
	 */
	private JishoParseData parse;
	
	/**
	 * A GSON instance to parse JSON
	 */
	private Gson gson;
	
	/**
	 * A constructor for a Jisho object which performs all necessary operations
	 */
	public Jisho() {
		this.parse = null;
	}

	/**
	 * 
	 * 
	 * @param searchWord the desired search word. Refer to Jisho.org for search methods
	 * @throws IOException
	 */
	public void search(String searchWord) throws IOException {

		URL url;
		
		this.searchWord = searchWord;
		this.jsonString = new String("");
		
		
		//Encode query string as searches with japanese characters are problematic.
		String word = URLEncoder.encode(searchWord, StandardCharsets.UTF_8.toString());
		url = new URL("https://jisho.org/api/v1/search/words?keyword=" + word);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();


		conn.setRequestMethod("GET");
		conn.connect();
		this.responseCode = conn.getResponseCode();

		if(this.responseCode != 200) {
			throw new RuntimeException("HttpResponseCode: " + responseCode);
		}


		Scanner input = new Scanner(url.openStream());
		input.useDelimiter(System.getProperty("line.separator")); //Prevent scanner from removing whitespace
		
		while(input.hasNext()) {
			jsonString += input.next();
		}
		
		input.close();
		

		 // Creating custom deserializer to keep each entry serialized and stored in an array until it is needed. 
		GsonBuilder builder = new GsonBuilder();
		
		builder.registerTypeAdapter(JishoParseData.class, deserializer);
		gson = builder.create();
		
		this.parse = gson.fromJson(this.jsonString, JishoParseData.class);
	}
	
	//An initial deserializer which separates the data and meta entries
	private JsonDeserializer<Jisho.JishoParseData> deserializer = new JsonDeserializer<JishoParseData>() {
		
		@Override
		public JishoParseData deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			
			JsonObject jsonObject = json.getAsJsonObject();
			
			return new Jisho.JishoParseData(
					
					jsonObject.get("data").getAsJsonArray(),
					jsonObject.get("meta").getAsJsonObject()
					
					
				);
				
		}	
	};

	public String getJsonString() {
		return new String(this.jsonString);
	}
	
	public String getStatus() {
		String status = new String(parse.meta.toString());
		return status.substring(10, status.length() - 1);
		
	}

	public int getNumEntries() {
		return parse.data.size();
	}
	
	//Potentially have each entry
	private Object getEntry(int entryNum) {
		return parse.data.get(entryNum);
	}
	
	
	public String getSlug(int entryNum) {
		JishoParseEntry tempParse = gson.fromJson(getEntry(entryNum).toString(), JishoParseEntry.class);
		return tempParse.slug;
	}
		
	public boolean getIsCommon(int entryNum) {
		JishoParseEntry tempParse = gson.fromJson(getEntry(entryNum).toString(), JishoParseEntry.class);
		return tempParse.is_common;
	}
	
	public int getTagsSize(int entryNum) {
		JishoParseEntry tempParse = gson.fromJson(getEntry(entryNum).toString(), JishoParseEntry.class);
		return tempParse.tags.size();
	}
	
	public String getTags(int entryNum, int tagNum) {
		JishoParseEntry tempParse = gson.fromJson(getEntry(entryNum).toString(), JishoParseEntry.class);
		return tempParse.tags.get(tagNum);
	}
	
	public int getJlptSize(int entryNum) {
		JishoParseEntry tempParse = gson.fromJson(getEntry(entryNum).toString(), JishoParseEntry.class);
		return tempParse.jlpt.size();
	}
	
	public String getJlpt(int entryNum, int jlptNum) {
		JishoParseEntry tempParse = gson.fromJson(getEntry(entryNum).toString(), JishoParseEntry.class);
		return tempParse.jlpt.get(jlptNum);
	}
	
	public int getJapaneseSize(int entryNum) {
		JishoParseEntry tempParse = gson.fromJson(getEntry(entryNum).toString(), JishoParseEntry.class);
		return tempParse.japanese.size();
	}
	
	private Object getJapanese(int entryNum, int japaneseNum) {
		JishoParseEntry tempParse = gson.fromJson(getEntry(entryNum).toString(), JishoParseEntry.class);
		return tempParse.japanese.get(japaneseNum);
	}
	
	public String getJapaneseWord(int entryNum, int japaneseNum) {
		JishoParseJapanese tempParse = gson.fromJson(getJapanese(entryNum, japaneseNum).toString(), JishoParseJapanese.class);
		return tempParse.word;
	}
	
	public String getJapaneseReading(int entryNum, int japaneseNum) {
		JishoParseJapanese tempParse = gson.fromJson(getJapanese(entryNum, japaneseNum).toString(), JishoParseJapanese.class);
		return tempParse.reading;
	}
	
	public int getSensesSize(int entryNum) {
		JishoParseEntry tempParse = gson.fromJson(getEntry(entryNum).toString(), JishoParseEntry.class);
		return tempParse.senses.size();
	}
	
	private Object getSenses(int entryNum, int sensesNum) {
		JishoParseEntry tempParse = gson.fromJson(getEntry(entryNum).toString(), JishoParseEntry.class);
		return tempParse.senses.get(sensesNum);
	}
	
	public int getEnglishDefinitionsSize(int entryNum, int sensesNum) {
		JishoParseSenses tempParse = gson.fromJson(getSenses(entryNum, sensesNum).toString(), JishoParseSenses.class);
		return tempParse.english_definitions.size();
	}
	
	public String getEnglishDefinitions(int entryNum, int sensesNum, int englishDefNum) {
		JishoParseSenses tempParse = gson.fromJson(getSenses(entryNum, sensesNum).toString(), JishoParseSenses.class);
		return tempParse.english_definitions.get(englishDefNum);
	}
	
	public int getPartsOfSpeechSize(int entryNum, int sensesNum) {
		JishoParseSenses tempParse = gson.fromJson(getSenses(entryNum, sensesNum).toString(), JishoParseSenses.class);
		return tempParse.parts_of_speech.size();
	}
	
	public String getPartsOfSpeech(int entryNum, int sensesNum, int partsOfSpeechNum) {
		JishoParseSenses tempParse = gson.fromJson(getSenses(entryNum, sensesNum).toString(), JishoParseSenses.class);
		return tempParse.parts_of_speech.get(partsOfSpeechNum);
	}
	
	public int getLinksSize(int entryNum, int sensesNum) {
		JishoParseSenses tempParse = gson.fromJson(getSenses(entryNum, sensesNum).toString(), JishoParseSenses.class);
		return tempParse.links.size();
	}
	
	public String getTagsSenses(int entryNum, int sensesNum) {
		JishoParseSenses tempParse = gson.fromJson(getSenses(entryNum, sensesNum).toString(), JishoParseSenses.class);
		return tempParse.tags.toString();
	}
	
	public String getRestrictions(int entryNum, int sensesNum) {
		JishoParseSenses tempParse = gson.fromJson(getSenses(entryNum, sensesNum).toString(), JishoParseSenses.class);
		return tempParse.restrictions.toString();
	}
	
	public String getSeeAlso(int entryNum, int sensesNum) {
		JishoParseSenses tempParse = gson.fromJson(getSenses(entryNum, sensesNum).toString(), JishoParseSenses.class);
		return tempParse.see_also.toString();
	}
	
	public String getAntonyms(int entryNum, int sensesNum) {
		JishoParseSenses tempParse = gson.fromJson(getSenses(entryNum, sensesNum).toString(), JishoParseSenses.class);
		return tempParse.antonyms.toString();
	}
	
	public String getSource(int entryNum, int sensesNum) {
		JishoParseSenses tempParse = gson.fromJson(getSenses(entryNum, sensesNum).toString(), JishoParseSenses.class);
		return tempParse.source.toString();
	}
	
	public String getInfo(int entryNum, int sensesNum) {
		JishoParseSenses tempParse = gson.fromJson(getSenses(entryNum, sensesNum).toString(), JishoParseSenses.class);
		return tempParse.info.toString();
	}
	
	public String getSentences(int entryNum, int sensesNum) {
		JishoParseSenses tempParse = gson.fromJson(getSenses(entryNum, sensesNum).toString(), JishoParseSenses.class);
		return tempParse.sentences.toString();
	}
	
	public String getAttribution(int entryNum, String key) {
		JishoParseEntry tempParse = gson.fromJson(getEntry(entryNum).toString(), JishoParseEntry.class);
		return tempParse.attribution.get(key).toString();
	}
	
	//A nested class used to store our various JSON objects as Java objects.
	private class JishoParseData {

		public JsonArray data;
		
		public JsonObject meta;

		public JishoParseData(JsonArray asJsonArray, JsonObject jsonObject) {
			this.data = asJsonArray;
			this.meta = jsonObject;
		}
		
	}

	private class JishoParseEntry{
		
		public String slug;
		
		private boolean is_common;
		
		private ArrayList<String> tags;
		
		private ArrayList<String> jlpt;
		
		private ArrayList<Object> japanese;
		
		private JsonArray senses;
		
		private Map<Object, Object> attribution;
		
		private String word;
		
		private String reading;
		
	}
	
	private class JishoParseJapanese{
		
		public String word;
		
		public String reading;
		
	}
	
	private class JishoParseSenses{
		
		public ArrayList<String> english_definitions;
		
		public ArrayList<String> parts_of_speech;
		
		public ArrayList<Object> links;
		
		public Object tags;
		
		public Object restrictions;
		
		public Object see_also;
		
		public Object antonyms;
		
		public Object source;
		
		public Object info;
		
		public Object sentences;
		
	}
	
	private class JishoParseLinks{
		
		public String text;
		
		public String url;
		
	}
}
