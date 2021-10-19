import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;


/*=============================================================================
|   Assignment:  pa01 - Encrypting a plaintext file using the Vigenere cipher
|
|       Author:  Ernesto Santos
|     Language:  Java
|
|   To Compile:  javac pa01.java
|
|   To Execute:  java -> java pa01 kX.txt pX.txt
|
|         Note:  All input files are simple 8 bit ASCII input
|
|        Class:  CIS3360 - Security in Computing - Fall 2021
|   Instructor:  McAlpin
|     Due Date:  Oct 24 2021
|+=============================================================================*/
public class pa01 
{
    final static int MAXSTRINGSIZE = 512;

    public static void main( String[] args )
    {
       File inData_key = new File(args[0]);
       File inData_pt = new File(args[1]);
       String key, plainText, cipherText;

       //Reads the input files and stores the contents into the string variables
       key = readData(inData_key);
       plainText = readData(inData_pt);


       //Formatted Strings
        key = formatData(key, false);
        plainText = formatData(plainText, true);

        cipherText = viginere(key, plainText);

        displayData(key, plainText, cipherText);

    }

    public static String readData(File data)
    {
        String readData = "";

        try {
            Scanner reader = new Scanner(data);
            while (reader.hasNextLine())
            {
                readData = readData.concat(reader.nextLine());
            }
            reader.close();
        } catch (FileNotFoundException e)
        {
            System.out.println("An Error has occurred while reading the file.\n" +
                    "Program will exit now.");
            e.printStackTrace();
            System.exit(0);
        }
        return readData;
    }

    public static String formatData(String data, Boolean isPlaintext)
    {
        String[] temp = data.split(" ");
        String finalData = "";
        int counter = 0;

        //Removes space from String and turns it into a single line
        while(counter < temp.length)
        {
            finalData = finalData.concat(temp[counter].toLowerCase(Locale.ROOT));
            counter++;
        }

        //Removes non-alphabetic characters
        counter = 0;
        char[] copyofkey = finalData.toCharArray();
        finalData = "";
        while (counter < copyofkey.length)
        {
            int charVal = copyofkey[counter];

            if(charVal <= 122 && charVal >= 97)
            {
                finalData = finalData.concat(String.valueOf(copyofkey[counter]));
            }

            counter++;
        }


        //Pads Plaintext Strings
        if(isPlaintext)
        {
            if(finalData.length() > MAXSTRINGSIZE)
            {
                finalData = finalData.substring(0,MAXSTRINGSIZE);
                return finalData;
            }

            counter = finalData.length();
            while (counter < MAXSTRINGSIZE)
            {
                finalData = finalData.concat("x");

                counter++;
            }
        }

        return finalData;
    }

    public static String viginere(String key, String pt)
    {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String cipherText = "";
        int loop_index = 0, key_index = 0;
        int cipher_index = 0;

        //Extends the key to fill the length of the plain text string
        while (key.length() < MAXSTRINGSIZE)
            key = key.concat(key);


        while (loop_index < MAXSTRINGSIZE)
        {
            int idex_1 = key.charAt(key_index++) - 97;
            int idex_2 = pt.charAt(loop_index) - 97;

            cipher_index = ((idex_1 + idex_2) % 26);

            cipherText = cipherText.concat(String.valueOf(alphabet.charAt(cipher_index)));

            loop_index++;
        }

        return cipherText;
    }

    public static void displayData(String key, String plainT, String cipherT)
    {
        char[] key_copy = key.toCharArray();
        char[] plain_copy = plainT.toCharArray();
        char[] cipher_copy = cipherT.toCharArray();

        System.out.println("\n\nVigenere Key: \n");
        for(int i = 0; i < key_copy.length; i++)
        {
            if(i != 0 && (i % 80) == 0)
                System.out.println("");

            System.out.print(key_copy[i]);
        }

        System.out.println("\n\nPlaintext: \n");
        for(int i = 0; i < plain_copy.length; i++)
        {
            if(i != 0 && (i % 80) == 0)
                System.out.println("");

            System.out.print(plain_copy[i]);
        }

        System.out.println("\n\nCiphertext: \n");
        for(int i = 0; i < cipher_copy.length; i++)
        {
            if(i != 0 && (i % 80) == 0)
                System.out.println("");

            System.out.print(cipher_copy[i]);
        }

	System.out.println("\n\n");
    }
}
/*=============================================================================
|     I Ernesto er922511 affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied  or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/
