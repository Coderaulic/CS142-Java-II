package ShippingHub;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Class:	 <b>WriteFile</b>
 *  File:	 WriteFile.java
 * <pre>
 *  Description: A class used to write parcel information data to a text file
 *               for later use by the application.
 *  @author:	 <i>Ryu Muthui</i>
 *  @see         java.io.FileNotFoundException;
 *  @see         java.io.FileWriter;
 *  @see         java.io.IOException;
 *  @see         java.io.PrintWriter;
 *  Environment: PC, Windows 7, jdk1.7_17, NetBeans 7.3
 *  Date:	 4/24/2013
 *  Hours:       2 hours
 *  @version	 1.01
 * </pre>
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class WriteFile 
{
    private FileWriter fileName;
    
    /**
     * Default constructor.
     * @param inputFileName
     * @throws IOException 
     */
    public WriteFile(String inputFileName) throws IOException 
    {
        try{fileName = new FileWriter(inputFileName, false);}
        catch(FileNotFoundException ex){}             
    }

    /**
     * Method: write -
     * A method to write to a variable using printWriter.
     * @param item 
     * @return void
     */
    public void write(String item)
    {
        PrintWriter output = new PrintWriter(fileName);
        output.println(item);
    }
    
    /**
     * Method: close-
     * A method to close the file to be called when finish writing to the file.
     * @return void
     */
    public void close()
    {
        try{fileName.close();}
        catch(IOException ex){ex.printStackTrace();}
    }
}
