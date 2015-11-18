import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//Wunderschöner Assembler - WORK IN PROGRESS -

public class Assembler {

	
	public static void main(String[] args) throws IOException
	  {
	    FileReader fr = new FileReader("Z:/git/NoNameAvailable/test.txt");
	    BufferedReader br = new BufferedReader(fr);
	    String currentLine =null;
	    
	    do{
	    	
	    	
	    currentLine = br.readLine();
	    if(currentLine!=null)System.out.println(currentLine);
	    
	    
	    }while(currentLine!=null);
	    
	    

	    br.close();
	  }
	}
