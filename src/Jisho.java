import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.Gson;


/**
 * Thoughts:
 * 
 * Is it the responsibility of the implementation to catch errors from the api wrapper?
 * Or should the wrapper catch its own errors?
 * 
 */

public class Jisho {

	private URL url;
	private String jsonString;
	private String searchWord;
	private int responseCode;
	private JishoParse parse;

	public Jisho() {}


	public void search(String searchWord) throws IOException {

		this.searchWord = searchWord;
		jsonString = new String("{\"meta\":{\"status\":200},\"data\":[{\"slug\":\"世界\",\"is_common\":true,\"tags\":[\"wanikani9\"],\"jlpt\":[\"jlpt-n4\"],\"japanese\":[{\"word\":\"世界\",\"reading\":\"せかい\"}],\"senses\":[{\"english_definitions\":[\"theworld\",\"society\",\"theuniverse\"],\"parts_of_speech\":[\"Noun\"],\"links\":[],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[]},{\"english_definitions\":[\"sphere\",\"circle\",\"world\"],\"parts_of_speech\":[],\"links\":[],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[]},{\"english_definitions\":[\"renowned\",\"world-famous\",\"well-knownoutsideofJapan\"],\"parts_of_speech\":[\"No-adjective\"],\"links\":[],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[]},{\"english_definitions\":[\"realmgovernedbyoneBuddha\",\"space\"],\"parts_of_speech\":[\"Noun\"],\"links\":[],\"tags\":[\"Buddhistterm\"],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[\"originalmeaning\"]},{\"english_definitions\":[\"World\"],\"parts_of_speech\":[\"Wikipediadefinition\"],\"links\":[{\"text\":\"Read“World”onEnglishWikipedia\",\"url\":\"http://en.wikipedia.org/wiki/World?oldid=495164614\"},{\"text\":\"Read“世界”onJapaneseWikipedia\",\"url\":\"http://ja.wikipedia.org/wiki/世界?oldid=42509363\"}],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[],\"sentences\":[]}],\"attribution\":{\"jmdict\":true,\"jmnedict\":false,\"dbpedia\":\"http://dbpedia.org/resource/World\"}},{\"slug\":\"世界大戦\",\"is_common\":true,\"tags\":[],\"jlpt\":[],\"japanese\":[{\"word\":\"世界大戦\",\"reading\":\"せかいたいせん\"}],\"senses\":[{\"english_definitions\":[\"theWorldWar\"],\"parts_of_speech\":[\"Noun\"],\"links\":[],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[]},{\"english_definitions\":[\"Worldwar\"],\"parts_of_speech\":[\"Wikipediadefinition\"],\"links\":[{\"text\":\"Read“Worldwar”onEnglishWikipedia\",\"url\":\"http://en.wikipedia.org/wiki/World_war?oldid=494536565\"},{\"text\":\"Read“世界大戦”onJapaneseWikipedia\",\"url\":\"http://ja.wikipedia.org/wiki/世界大戦?oldid=40848094\"}],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[],\"sentences\":[]}],\"attribution\":{\"jmdict\":true,\"jmnedict\":false,\"dbpedia\":\"http://dbpedia.org/resource/World_war\"}},{\"slug\":\"世界観\",\"is_common\":true,\"tags\":[],\"jlpt\":[],\"japanese\":[{\"word\":\"世界観\",\"reading\":\"せかいかん\"}],\"senses\":[{\"english_definitions\":[\"worldview\",\"outlookontheworld\",\"Weltanschauung(philosophy)\"],\"parts_of_speech\":[\"Noun\"],\"links\":[],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[]},{\"english_definitions\":[\"appearanceofaworld(e.g.infiction)\"],\"parts_of_speech\":[],\"links\":[],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[]},{\"english_definitions\":[\"Worldview\"],\"parts_of_speech\":[\"Wikipediadefinition\"],\"links\":[{\"text\":\"Read“Worldview”onEnglishWikipedia\",\"url\":\"http://en.wikipedia.org/wiki/World_view?oldid=494555694\"},{\"text\":\"Read“世界観”onJapaneseWikipedia\",\"url\":\"http://ja.wikipedia.org/wiki/世界観?oldid=42678442\"}],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[],\"sentences\":[]}],\"attribution\":{\"jmdict\":true,\"jmnedict\":false,\"dbpedia\":\"http://dbpedia.org/resource/World_view\"}},{\"slug\":\"世界的\",\"is_common\":true,\"tags\":[],\"jlpt\":[],\"japanese\":[{\"word\":\"世界的\",\"reading\":\"せかいてき\"}],\"senses\":[{\"english_definitions\":[\"worldwide\",\"global\",\"international\",\"universal\"],\"parts_of_speech\":[\"Na-adjective\"],\"links\":[],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[]},{\"english_definitions\":[\"world-famous\",\"world-class\"],\"parts_of_speech\":[],\"links\":[],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[]}],\"attribution\":{\"jmdict\":true,\"jmnedict\":false,\"dbpedia\":false}},{\"slug\":\"世界一\",\"is_common\":true,\"tags\":[],\"jlpt\":[],\"japanese\":[{\"word\":\"世界一\",\"reading\":\"せかいいち\"}],\"senses\":[{\"english_definitions\":[\"bestintheworld\"],\"parts_of_speech\":[\"Adverbialnoun\",\"Temporalnoun\"],\"links\":[],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[]}],\"attribution\":{\"jmdict\":true,\"jmnedict\":false,\"dbpedia\":false}},{\"slug\":\"世界中\",\"is_common\":true,\"tags\":[],\"jlpt\":[],\"japanese\":[{\"word\":\"世界中\",\"reading\":\"せかいじゅう\"},{\"word\":\"世界じゅう\",\"reading\":\"せかいじゅう\"}],\"senses\":[{\"english_definitions\":[\"aroundtheworld\",\"throughouttheworld\"],\"parts_of_speech\":[\"Noun\",\"No-adjective\"],\"links\":[],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[]}],\"attribution\":{\"jmdict\":true,\"jmnedict\":false,\"dbpedia\":false}},{\"slug\":\"世界一周\",\"is_common\":false,\"tags\":[],\"jlpt\":[],\"japanese\":[{\"word\":\"世界一周\",\"reading\":\"せかいいっしゅう\"}],\"senses\":[{\"english_definitions\":[\"round-the-worldtrip\",\"circumnavigation\",\"globe-trotting\"],\"parts_of_speech\":[\"Noun\"],\"links\":[],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[]},{\"english_definitions\":[\"Circumnavigation\"],\"parts_of_speech\":[\"Wikipediadefinition\"],\"links\":[{\"text\":\"Read“Circumnavigation”onEnglishWikipedia\",\"url\":\"http://en.wikipedia.org/wiki/Circumnavigation?oldid=495369382\"},{\"text\":\"Read“世界一周”onJapaneseWikipedia\",\"url\":\"http://ja.wikipedia.org/wiki/世界一周?oldid=41990970\"}],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[],\"sentences\":[]}],\"attribution\":{\"jmdict\":true,\"jmnedict\":false,\"dbpedia\":\"http://dbpedia.org/resource/Circumnavigation\"}},{\"slug\":\"世界記録\",\"is_common\":false,\"tags\":[],\"jlpt\":[],\"japanese\":[{\"word\":\"世界記録\",\"reading\":\"せかいきろく\"}],\"senses\":[{\"english_definitions\":[\"worldrecord\",\"establishingaworldrecord(in)\"],\"parts_of_speech\":[\"Noun\",\"Suruverb\"],\"links\":[],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[]},{\"english_definitions\":[\"Worldrecord\"],\"parts_of_speech\":[\"Wikipediadefinition\"],\"links\":[{\"text\":\"Read“Worldrecord”onEnglishWikipedia\",\"url\":\"http://en.wikipedia.org/wiki/World_record?oldid=494194522\"},{\"text\":\"Read“世界記録”onJapaneseWikipedia\",\"url\":\"http://ja.wikipedia.org/wiki/世界記録?oldid=42748980\"}],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[],\"sentences\":[]}],\"attribution\":{\"jmdict\":true,\"jmnedict\":false,\"dbpedia\":\"http://dbpedia.org/resource/World_record\"}},{\"slug\":\"世界経済\",\"is_common\":false,\"tags\":[],\"jlpt\":[],\"japanese\":[{\"word\":\"世界経済\",\"reading\":\"せかいけいざい\"}],\"senses\":[{\"english_definitions\":[\"worldeconomy\",\"internationaleconomy\"],\"parts_of_speech\":[\"Noun\"],\"links\":[],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[]},{\"english_definitions\":[\"Worldeconomy\"],\"parts_of_speech\":[\"Wikipediadefinition\"],\"links\":[{\"text\":\"Read“Worldeconomy”onEnglishWikipedia\",\"url\":\"http://en.wikipedia.org/wiki/World_economy?oldid=492740220\"},{\"text\":\"Read“世界経済”onJapaneseWikipedia\",\"url\":\"http://ja.wikipedia.org/wiki/世界経済?oldid=42435803\"}],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[],\"sentences\":[]}],\"attribution\":{\"jmdict\":true,\"jmnedict\":false,\"dbpedia\":\"http://dbpedia.org/resource/World_economy\"}},{\"slug\":\"世界平和\",\"is_common\":false,\"tags\":[],\"jlpt\":[],\"japanese\":[{\"word\":\"世界平和\",\"reading\":\"せかいへいわ\"}],\"senses\":[{\"english_definitions\":[\"worldpeace\",\"peaceoftheworld\"],\"parts_of_speech\":[\"Noun\"],\"links\":[],\"tags\":[],\"restrictions\":[],\"see_also\":[],\"antonyms\":[],\"source\":[],\"info\":[]}],\"attribution\":{\"jmdict\":true,\"jmnedict\":false,\"dbpedia\":false}}]}\r\n" + 
				"");

		/*
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
		while(input.hasNext()) {
			jsonString += input.next();
		}
		input.close();
		 */

		Gson gson = new Gson();
		this.parse = gson.fromJson(this.jsonString, JishoParse.class);

	}


	//Potentially have each entry
	public Object getEntry(int entryNum) {
		return parse.getData().get(entryNum);
	}

	public int getNumEntries() {
		return parse.getData().size();
	}
	
	public String getJsonString() {
		return new String(this.jsonString);
	}

	
	

	/* 
	 * A nested class used to store our various JSON objects as Java objects
	 * 
	 */

	private class JishoParse {

		private ArrayList<Object> data;
		private Object meta;
		private String slug;
		private String tags;
		private String jlpt;
		private Map<String, String> japanese;
		private ArrayList<Object> senses;
		private Map<String, String> attribution;

		public ArrayList<Object> getData(){
			return this.data;
		}
		
		public Object getMeta() {
			return this.meta;
		}

		public String getSlug() {
			return this.slug;
		}
		
	}


}
