import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class hex {
	
	public static String stringToHex(String str) {
		  char[] chars = str.toCharArray();
		  
		  StringBuffer hex = new StringBuffer();
                  boolean op = false;
		  for(int i = 0; i < chars.length; i++) {
                    
                    if (chars[i] == ' ') {
                        op = true;
                        continue;
                    }
                    if (op) {
                        if ((chars[i]) < 58 && (chars[i]) > 47)
                            hex.append((int)chars[i] - 48);
                        else
                            hex.append(chars[i]);
                    } else {
                        hex.append(Integer.toHexString((int)chars[i]).toUpperCase());
                    }
		  }
	 
		  return hex.toString();
	}

        public static String stringToAscii(String hex){

            StringBuilder sb = new StringBuilder();
            StringBuilder temp = new StringBuilder();


            for( int i=0; i<hex.length()-1; i+=2 ){


            String output = hex.substring(i, (i + 2));

                int decimal = Integer.parseInt(output, 16);
                if (decimal < 91 && decimal > 64) {
                    sb.append((char)decimal);
                }
                else {
                    sb.append(decimal);
                }

                temp.append(decimal);
            }


            return sb.toString();
         }

        public static void fileToHex(String inFile, String outFile) {
            try {
                FileInputStream inStream = new FileInputStream(inFile);
                FileWriter outStream = new FileWriter(outFile);
                DataInputStream in = new DataInputStream(inStream);
                BufferedWriter out = new BufferedWriter(outStream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                String strLine;
           
                while ((strLine = br.readLine()) != null) {

 
                        out.write(stringToHex(strLine) + "\n");
                    
                }
                out.close();
                in.close();
	    } catch (FileNotFoundException e) {
                System.err.println("Error: " + e.getMessage());
	    } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        public static void fileToAscii(String inFile, String outFile) {
            try {
                FileInputStream inStream = new FileInputStream(inFile);
                FileWriter outStream = new FileWriter(outFile);
                DataInputStream in = new DataInputStream(inStream);
                BufferedWriter out = new BufferedWriter(outStream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                String strLine;
                int counter = 0;
                while ((strLine = br.readLine()) != null) {
                    out.write(stringToAscii(strLine) + "\n");
                }
                out.close();
                in.close();
	    } catch (FileNotFoundException e) {
                System.err.println("Error: " + e.getMessage());
	    } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
	

}
