import java.io.IOException;
import java.util.Scanner;

public class driver {

	public static void main(String[] args) throws IOException{
		
		Jisho search = new Jisho();
		Scanner input = new Scanner(System.in);
		String str = input.nextLine();
		
		search.search(str);
		
		
		
	}	
}
