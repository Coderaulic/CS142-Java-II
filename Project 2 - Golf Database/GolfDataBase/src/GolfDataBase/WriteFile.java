package GolfDataBase;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  Class:	<b>WriteFile</b>
*  File:	WriteFile.java
* <pre>
*  Description:	A class used to write member information into an external txt file.
*  @author:	<i>Ryu Muthui and Siki Zhang</i>
*  Environment:	PC, Windows 7, jdk1.7_17, NetBeans 7.3
*  Date:	4/11/2013
*  Hours:       45 minutes
*  @version	1.01
*  @see       	java.io.FileWriter;
*  @see       	java.io.FileNotFoundException;
*  @see         java.io.IOException;
*  @see         java.io.PrintWriter;
* </pre>
*  History Log:	Started 4/30/2013, Completed 5/15/2013.
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class WriteFile 
{
      //instance variable  
      private FileWriter fileName;

     /**
     * Default constructor.
     * @param inputFileName
     * @throws printStackTrace
     * @throws IOException 
     */
      public WriteFile(String inputFilename) throws IOException
      {
        try
        {
            fileName = new FileWriter (inputFilename, false);
        }
        catch(FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
      }
    
    /**
     * Method: Write -
     * A method to write to a variable using printWriter.
     * @param item 
     * @return void
     */
    public void Write(String item)
    {
        PrintWriter output = new PrintWriter (fileName);
        output.println(item);
    }
    
    /**
     * Method: close-
     * A method to close the file to be called when finish writing to the file.
     * @return void
     */
    public void close()
    {
        try
        {
            fileName.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
     }
}
