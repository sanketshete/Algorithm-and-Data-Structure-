import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Decoding_File {

	public static void main(String[] args) {

		int bitLength=Integer.parseInt(args[1]);
		List<Integer> input= new ArrayList<Integer>();
		double encoded_val=0;
		String inputFileName=args[0];
		
		try {	
			InputStream fs= new FileInputStream(inputFileName);
			Reader ow= new InputStreamReader(fs, "UTF-16BE");
			BufferedReader bufferedReader=new BufferedReader(ow);
			while((encoded_val=bufferedReader.read())!=-1){	 //read the input file
				input.add((int) encoded_val);
			}
			decoding(input,bitLength,inputFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static void decoding(List<Integer> input,int bitLength,String inputFileName) {

		Map<Integer,String> table = new HashMap<Integer,String>();
		int MAX_TABLE_SIZE=(int) Math.pow(2, bitLength);

		for(int i=0;i<256;i++){
			table.put(i,""+(char)i);//initialize the table with characters and it's ascii value
		}
		
		int sizeOfTable=255;
		int code=input.get(0);
		String newString=null;
		String oldString=""+table.get(code);
		StringBuffer sb = new StringBuffer(oldString);
		for(int i=1;i<input.size();i++){
			 code=input.get(i); //get next Input code
			
			 if(table.containsKey(code)){
				 newString=table.get(code);

			 }else{
				 newString= oldString+oldString.charAt(0);
			 }
			 sb.append(newString);
			 if(table.size()<MAX_TABLE_SIZE){ //check whether the table is full or not
				 	sizeOfTable++;
					table.put(sizeOfTable,(oldString+newString.charAt(0)));//add the new code into the table
			 }
			 oldString=newString;
		}
		System.out.println(sb);
		writeToFile(sb.toString(),inputFileName);
	}
	
private static void writeToFile(String output,String inputFileName) {
		
		String outputFilename= inputFileName.substring(0, inputFileName.indexOf("."))+"_decode.txt";
		try {
			FileWriter fileWriter= new FileWriter(outputFilename);
			BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
			bufferedWriter.write(output);
			bufferedWriter.flush();
			bufferedWriter.close();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}
}
