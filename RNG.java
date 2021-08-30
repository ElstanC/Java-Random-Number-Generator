import java.time.*;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
public class MainFinal {
	
	
	public static void main(String[] args) {

	//Gets current time Object
	LocalDateTime time = LocalDateTime.now();
    Scanner sc = new Scanner(System.in);
	
	//Creation of 3 seed variables using time Object
	int seed1 = time.getNano() + time.getDayOfYear()+time.getSecond()*time.getSecond()^time.getHour() * time.getHour()^time.getMinute()*time.getNano() >> 16;
	int seed2 = time.getNano() + time.getDayOfYear()+time.getSecond()*time.getSecond()^time.getHour() * time.getHour()^time.getMinute()*time.getNano() >> 1;
	int seed3 =  time.getNano() + time.getDayOfYear()+time.getSecond()*time.getSecond()^time.getHour() * time.getHour()^time.getMinute()*time.getNano() >> 2;

	System.out.print("Number of keys: ");
	int numOfKeys = sc.nextInt();
	System.out.print("Length of keys: ");
	int lengthOfKeys = sc.nextInt();
	
	sc.close();
	//main method & parameters for generation
	keyGenerator(numOfKeys, lengthOfKeys, seed1, seed2, seed3);

	
	}
	
	/*
	 * n = Number of keys to be generated
	 * keySize = bit length of the final key
	 */
	public static void keyGenerator(int n, int keySize, int seed1, int seed2, int seed3) {
		//increments & keeps track of the number of keys being generated 
		int numOfKeys = 0;
		//the temporary key that will be concatenated to the final key
		String tempKey;
		//the final key that will be output to the .txt
		String finalKey = null;
		
		//Start of the try catch block for file writer to output Final keys into a .txt file
		try {
		FileWriter fw = new FileWriter("C:\\Users\\elsta\\PycharmProjects\\RandomNumGen\\prngOutput.txt");
		

		

		
		while (numOfKeys < n) {
			
			//Linear generator for 3 separate seed variables
			seed1 = (27953191 * seed1 + 8963411) & 0x7fffffff;
			seed2 = (27953191 * seed2 + 8963411) & 0x7fffffff;
			seed3 = (27953191 * seed3 + 8963411) & 0x7fffffff;
			
			//Adds 3 seed variables together to create the temporary key
			seed1 = seed1 + seed2 + seed3 & 0x7fffffff;
			
			//Converts the temporary key to a string for concatenation for final key generation
			tempKey = Integer.toString(seed1);
			
			//Start of the final key generation and output generator
			//check if final key is null if true then set the final key equal to temporary key
			if(finalKey == null) {
				finalKey = tempKey;
			}
			
		
			try {
				//if final key is too small and final key isn't the same as the temporary key add temporary key to the final key as long as
				if(finalKey.length() < keySize && finalKey != tempKey) {
					finalKey += tempKey;
					}
				/*
				 * if the length of the final key is bigger than the designated key size shorten the 
				 * length using the substring method, starting from the beginning to the end of created key
				 */
				if(finalKey.length() > keySize) {
					finalKey = finalKey.substring((finalKey.length() - keySize), finalKey.length());
					}
				//if the final key is the correct size then write the key to the .txt file and increment the number of keys by 1
				if(finalKey.length() == keySize) {
					fw.write(finalKey + "\n");
					numOfKeys += 1;
					finalKey = null;
					}
				}
				
				//catch exception for file writer
				catch(IOException e) {
					
				}
			
		}
			//closes the file writer
			fw.close();
			System.out.println("\nDone:\n" + "Keys Generated: " + n + "\nLength of Keys: " + keySize);
		}
		//catch exception for file writer
		catch(IOException e) {
			
		}
	}
 
}
