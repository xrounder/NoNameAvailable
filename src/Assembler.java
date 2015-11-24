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
	    
	    
	    //reads the given text-file, line by line, until text-file is finished
	    do{
	    	
	    	
	    currentLine = br.readLine();
	    
	    
	    
	    }while(currentLine!=null);
	    
	    

	    br.close();
	  }
	}
