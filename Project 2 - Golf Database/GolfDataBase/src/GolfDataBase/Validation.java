package GolfDataBase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  Class:	<b>Validation</b>
*  File:	Validation.java
* <pre>
*  Description:	A class used to validate the potential phonenumber and email values 
*               that the user may try to input.
*  @author:	<i>Ryu Muthui</i>
*  Environment:	PC, Windows 7, jdk1.7_17, NetBeans 7.3
*  Date:	5/15/2013
*  Hours:       1 hours
*  @version	1.01
*  @see         java.util.regex.Matcher
*  @see         java.util.regex.Pattern
* </pre>
*  History Log:	Started 5/14/2013, Completed 5/15/2013.
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class Validation
{
    /**
     * Method: isEmail -
     * A method that uses the Pattern and Matcher utility tools with regular expression
     * to validate the email address.
     * @param fieldValue
     * @return boolean
     * Author Ryu Muthui
     */
    public static boolean isEmail(String fieldValue)
    {
        Pattern pat = Pattern.compile("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$");
        Matcher mat = pat.matcher(fieldValue);
        return mat.matches();
    }
    
    /**
     * Method: isNumber -
     * A method that uses the Pattern and Matcher utility tools with regular expression
     * to validate the phone number values.
     * @param fieldValue
     * @return boolean
     * Author Ryu Muthui
     */
    public static boolean isNumber(String fieldValue)
    {
         Pattern pat = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
         Matcher mat = pat.matcher(fieldValue);
         return mat.matches();    
    }
}
