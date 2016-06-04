import java.io.*;
import java.util.*;

/*This class is for handling input/output from storage*/
public class Storage{
	private static File csvFile;
	private static final String FILE_NAME = "speech.csv";
	
	private BufferedReader reader;
	private BufferedWriter writer;
	
	private boolean isHeaderRead;
	private HashMap<String, String> lineRead;
	private String[] header;
	private String lineWrite;
	
	public Storage(){
		initializeFile();
		isHeaderRead = false;
		try {
			writer = new BufferedWriter(new FileWriter(csvFile,true));
		} catch (IOException e) {}
	}
	
	/*dealing with read*/
	public void readHeaders(){
		try{
			if (reader == null) {
				reader = new BufferedReader(new FileReader(csvFile));
				StringTokenizer line = new StringTokenizer(reader.readLine(),",");
				header = new String[line.countTokens()];
				for (int i=0; i<header.length; ++i){
					header[i] = line.nextToken();
				}
				reader.mark(0);
				isHeaderRead = true;
			}
		}catch(FileNotFoundException e){
		}catch (IOException e) {
		}
	}
	
	public boolean readRecords(){
		if(isHeaderRead){
			try {
				lineRead = new HashMap<String, String>();
				StringTokenizer line = new StringTokenizer(reader.readLine(),",");
				for(int i=0; i<header.length; ++i){
					lineRead.put(header[i], line.nextToken());
				}
				return true;
			} catch (IOException e) {
				return false; //end of file reading
			}
		}
		return false;
	}
	
	public String get(String title){
		return lineRead.get(title);
	}
	
	public void readClose(){
		try {
			reader.close();
			reader = null;
		} catch (IOException e) {}
	}	
	
	public void readReset(){
		try {
			if (reader != null) reader.reset();
		} catch (IOException e) {}
	}
	
	/*dealing with write*/
	public void write(String content){
		if(lineWrite == null){
			lineWrite = content;
		}else{
			lineWrite = lineWrite + "," + content;
		}
	}
	
	public void endRecord(){
		try {
			lineWrite = lineWrite + "\n";
			System.out.println(lineWrite);
			if (writer == null){
				writer = new BufferedWriter(new FileWriter(csvFile,true));
			}
			writer.write(lineWrite);
			lineWrite = null;
		} catch (IOException e) {}
	}
	
	public void writeClose(){
		try {
			if(writer != null) writer.close();
			writer = null;
		} catch (IOException e) {}
	}
	
	public void cleanFile(){
		try {
			writer = new BufferedWriter(new FileWriter(csvFile));
			writer.close();
			writer = null;
		} catch (IOException e) {}
	}
	
	/*BackEnd*/
	private void initializeFile(){
		csvFile = new File(FILE_NAME);
		if(!csvFile.exists()){
			try {
				csvFile.createNewFile();
			} catch (IOException e) {}
		}
	}
}