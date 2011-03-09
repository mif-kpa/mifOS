/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FileUtilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 *
 * @author Karolis Voicechovskis PS3
 */
public class FileUtilities
{
    private static final int MAX_PROGRAM_LENGTH = 256;

    public static int[] getDataFromFile(File file) throws IOException
    {
        Scanner scanner = new Scanner(file);
        
        long length = file.length();

        //Tinkriname ar programos kodas nera didesnis nei
        if (length > MAX_PROGRAM_LENGTH)
        {
            //Failas per didelis. Exception'as!!!
        }

        int c;
        int[] data = new int[(int)length / 4];
        int index = 0;

        while (scanner.hasNextInt())
        {
            data[index++] = scanner.nextInt();
        }

       
        scanner.close();
        
        return data;
    }

}
