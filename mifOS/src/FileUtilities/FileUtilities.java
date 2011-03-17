/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FileUtilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author Karolis Voicechovskis PS3
 */
public class FileUtilities
{
    private static final int MAX_PROGRAM_LENGTH = 256;

    public static int[] getDataFromFile(File file) throws IOException
    {
        FileInputStream fileInputStream = new FileInputStream(file);
        
        long length = file.length();

        //Tinkriname ar programos kodas nera didesnis nei MAX_PROGRAM_LENGTH
        if (length > MAX_PROGRAM_LENGTH)
        {
            //Failas per didelis. Exception'as!!!
        }

        int c = 0;
        int word = 0;
        int[] data = new int[(int)length / 4];
        int base = 3;
        int index = 0;

        while ((c = fileInputStream.read()) != -1)
        {
            c = c << base * 8;
            word += c;
            base--;
            
            if (base == -1)
            {
                base = 3;
                index++;
                data[index] = word;
                word = 0;
            }
        }

        fileInputStream.close();
        
        return data;
    }

}
