package GolfDataBase;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  Class:	<b>MemberInformation</b>
*  File:	MemberInformation.java
* <pre>
*  Description:	This class is created when using to store information about the 
*               Golf Club member's information.
*  @author:	<i>Ryu Muthui and Siqi Zhang</i>
*  Environment:	PC, Windows 7, jdk1.7_17, NetBeans 7.3
*  Date:	5/15/2013
*  Hours:       1/2 hour
*  @version	1.01
* </pre>
*  History Log:	Started 4/30/2013, Completed 5/15/2013.
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class MemberInformation 
{
    //instance variables
    private String firstName, lastName, eMail, phoneNumber; 
    private int memberRank;

    /**
     * Default constructor declaring initial values.
     */
    public MemberInformation()
    {
        firstName = ""; 
        lastName = "";
        eMail = "";
        phoneNumber = "";
        memberRank = 0;
     }
    
    /**
     * Overloaded constructor
     * @param firstName
     * @param lastName
     * @param eMail
     * @param phoneNumber
     * @param memberRank
     */
    public MemberInformation (String firstName, String lastName, String eMail,
            String phoneNumber, int memberRank)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.phoneNumber = phoneNumber;
        this.memberRank = memberRank;
    }

    /**
     * Method setFirstName -
     * A method to set the firstName value.
     * @param firstName 
     * @return void
     */
    public void setFirstName(String firstName) 
    {this.firstName = firstName;}

    /**
     * Method getFirstName -
     * A method to get the firstName value.
     * @return firstName
     */
    public String getFirstName() 
    {return firstName;}

    /**
     * Method setLastName -
     * A method to set the lastName value.
     * @param lastName 
     * @return void
     */
    public void setLastName(String lastName) 
    {this.lastName = lastName;}

    /**
     * Method getLastName -
     * A method to get the lastName value.
     * @return lastName
     */
    public String getLastName() 
    {return lastName;}

    /**
     * Method setEmail -
     * A method to set the email value.
     * @param eMail 
     * @return void
     */
    public void setEmail(String eMail) 
    {this.eMail = eMail;}

    /**
     * Method getEmail -
     * A method to get the eMail value.
     * @return eMail
     */
    public String getEmail() 
    {return eMail;}

    /**
     * Method setPhoneNumber -
     * A method to set the phoneNumber value.
     * @param phoneNumber 
     * @return void
     */
    public void setPhoneNumber(String phoneNumber) 
    {this.phoneNumber = phoneNumber;}

    /**
     * Method getPhoneNumber -
     * A method to get the phoneNumber value.
     * @return phoneNumber
     */
    public String getPhoneNumber() 
    {return phoneNumber;}

    /**
     * Method setRank -
     * A method to set the memberRank value.
     * @param memberRank 
     * @return void
     */
    public void setRank(int memberRank) 
    {this.memberRank = memberRank;}

    /**
     * Method getRank -
     * A method to get the memberRank value.
     * @return memberRank
     */
    public int getRank() 
    {return memberRank;}

    
    /**
     * A custom toString method. Not used.
     * @return 
     */
    @Override
    public String toString()
    {
        return this.firstName + ", " + this.lastName + ", " + this.eMail + ", " + 
                this.phoneNumber + ", " + this.memberRank + "."; 
    }

}
