import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Encoding_File {

	public static void main(String[] args) {
		String input = "";
		String line;
		
		String inputFileName=args[0]; //taking the first argument as a file name
		int bitLength=Integer.parseInt(args[1]); //taking the second argument as a bit length		
		try {
			//read the input file
			FileReader fileReader= new FileReader(inputFileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line=bufferedReader.readLine()) != null){
				input=line+input;
			} 
			encoding(input,bitLength,inputFileName);
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void encoding(String input,int bitLength,String inputFileName) {
		
		Map<String,Integer> table = new HashMap<String,Integer>();
		int MAX_TABLE_SIZE=(int) Math.pow(2, bitLength);
		
		List<Integer> output= new ArrayList<Integer>();

		for(int i=0;i<256;i++){
			table.put((char)i+"", i); //initialize the table with characters and it's ascii value
		}
		
		int sizeOfTable=255;
		String newString = "";
		
		for(int i=0;i<input.length();i++){
			char symbol=input.charAt(i); //get next Input Symbol			
			if(table.containsKey(newString+symbol)){
				newString=newString+symbol;
			}else{
					output.add(table.get(newString)); 
					if(table.size()<MAX_TABLE_SIZE){ //check whether the table is full or not
						sizeOfTable++;
						table.put((newString+symbol), sizeOfTable);//add the new code into the table 
						}
					newString=symbol+"";
				}
		}	
		output.add(table.get(newString));
		for(int i=0;i<output.size();i++){
			System.out.println(output.get(i));
		}
		writeToFile(output,inputFileName);
	}

	private static void writeToFile(List<Integer> output,String inputFileName) {
		
		String outputFilename= inputFileName.substring(0, inputFileName.indexOf("."))+".lzw";
		try {
			FileWriter fileWriter= new FileWriter(outputFilename);
			FileOutputStream fs= new FileOutputStream(outputFilename);
			OutputStreamWriter ow= new OutputStreamWriter(fs, "UTF_16BE");
			BufferedWriter bufferedWriter=new BufferedWriter(ow);
			for(int i=0;i<output.size();i++){
			bufferedWriter.write(output.get(i)); //write the output into the file in UTF_16BE format
			}
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}

}
