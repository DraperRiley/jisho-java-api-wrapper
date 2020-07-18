import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class Jisho {

	private URL url;
	private String jsonString;
	
	public Jisho() {}
	
	
	public void search(String searchWord) throws IOException {
		
		jsonString = new String("");
	
		//Encode query string as searches with japanese characters are problematic.
		String word = URLEncoder.encode(searchWord, StandardCharsets.UTF_8.toString());
		url = new URL("https://jisho.org/api/v1/search/words?keyword=" + word);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		
		
		conn.setRequestMethod("GET");
		conn.connect();
		int responseCode = conn.getResponseCode();
		
		if(responseCode != 200) {
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
