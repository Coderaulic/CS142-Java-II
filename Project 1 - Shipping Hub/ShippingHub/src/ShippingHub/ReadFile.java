package ShippingHub;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Class:	 <b>ReadFile</b>
 *  File:	 ReadFile.java
 * <pre>
 *  Description: A class used to write parcel information data to a text file
 *               for later use by the application.
 *  @author:	 <i>Ryu Muthui</i>
 *  @see         java.io.BufferedReader;
 *  @see         java.io.FileNotFoundException;
 *  @see         java.io.FileReader;
 *  @see         java.io.IOException;
 *  @see         javax.swing.JOptionPane;
 *  Environment: PC, Windows 7, jdk1.7_17, NetBeans 7.3
 *  Date:	 4/24/2013
 *  Hours:       1 hours
 *  @version	 1.01
 * </pre>
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class ReadFile 
{
    //instance vairiables
    private FileReader inputFile;
    private BufferedReader input;
    private boolean fileExists;
    
    /**
     * Default Constructor
     * @param inFileName 
     */
    public ReadFile(String inFileName)
    {
        try
        {
            inputFile = new FileReader(inFileName);
            input = new BufferedReader(inputFile);
            fileExists = true;
        }
        catch(FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: File Not Found.", 
            "File Open Error", + JOptionPane.WARNING_MESSAGE);  
            fileExists = false;
        }
    }

    /**
     * Method readRecord-
     * A method to read a line at a time from the text file.
     * @return line
     */
    public String readRecord()
    {
        String line = "";
        try{line = input.readLine();}
        catch(IOException ex){ex.printStackTrace();}
        return line;         
    }
    
    /**
     * Method: close-
     * A method to close the file when finished reading to the file.
     * @return void
     */
    public void close()
    {
        try{inputFile.close();}
        catch(IOException ex){ex.printStackTrace();} 
    }
}
