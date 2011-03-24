/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FileUtilities;

import Exception.MifOSException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author Karolis Voicechovskis PS3
 */
public class FileUtilities
{
    private static final int MAX_PROGRAM_LENGTH = 0xFFFF;

    public static int[] getDataFromFile(File file) throws MifOSException
    {
        try
        {
            FileInputStream fileInputStream = new FileInputStream(file);

            long length = file.length();

            //Tinkriname ar programos kodas nera didesnis nei MAX_PROGRAM_LENGTH
            if (length > MAX_PROGRAM_LENGTH)
            {
                String msg = "FileUtilities.getDataFromFile: \n"
                        + "Įkeliama programa per didelė.\n "
                        + "Maksimalus dydis - " + MAX_PROGRAM_LENGTH
                        + ", bandoma įkelti - " + length / 4;
                throw new MifOSException(msg);
            }

            int c = 0;
            int word = 0;
            int[] data = new int[(int)length / 4];
            int base = 3;
            int index = 0;

            while ((c = fileInputStream.read()) != -1)
            {
                c = c << (base * 8);
                word += c;
                base--;

                if (base == -1)
                {
                    base = 3;
                    data[index] = word;
                    index++;
                    word = 0;
                }
            }

            fileInputStream.close();

            return data;

        } catch(IOException e)
        {
            String msg = "FileUtilities.getDataFromFile: \n"
                          + "Klaida nuskaitant failą";
            throw new MifOSException(msg);
        }
    }

}
