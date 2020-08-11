import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


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
	
	public Jisho() {}
	
	
	public void search(String searchWord) throws IOException {
		
		this.searchWord = searchWord;
		jsonString = new String("");
	
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
		
		
		System.out.println(jsonString);
		
	}
	
}
