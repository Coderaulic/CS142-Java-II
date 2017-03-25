package ShippingHub;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  Class:	<b>ParcelInformation</b>
*  File:	ParcelInformation.java
* <pre>
*  Description:	This class is created to package object to store information about
*               the who/when/where the package is going to be sent.
*  @author:	<i>Ryu Muthui</i>
*  Environment:	PC, Windows 7, jdk1.7_17, NetBeans 7.3
*  Date:	4/24/2013
*  Hours:       1/2 hours
*  @version	1.01
* </pre>
*  History Log:	Created on April 9, 2013, Completed April 24th 2013.
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class ParcelInformation 
{
    //instance variables
    int parcelID; 
    private String name, address, city, state, zip, arrivalTime;

    /**
     * Default constructor declaring initial values.
     */
    public ParcelInformation()
    {
        parcelID = 0;
        name = ""; 
        address = "";
        city = "";
        state = "";
        zip = "";
        arrivalTime = "";
    }
    
    /**
     * Overloaded constructor
     * @param parcelID
     * @param name
     * @param address
     * @param city
     * @param state
     * @param zip
     * @param arrivalTime 
     */
    public ParcelInformation (int parcelID, String name, String address, String city, 
            String state,String zip, String arrivalTime)
    {
        this.parcelID = parcelID;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.arrivalTime = arrivalTime;
    }

    /**
     * Method setParcelID -
     * A method to set the parcelID value.
     * @param parcelID 
     * @return void
     */
    public void setParcelID(int parcelID) 
    {this.parcelID = parcelID;}

    /**
     * Method getParcelID -
     * A method to get the parcelID value.
     * @return parcelID
     */
    public int getParcelID() 
    {return parcelID;}

    /**
     * Method setName -
     * A method to set the name value.
     * @param name 
     * @return void
     */
    public void setName(String name) 
    {this.name = name;}

    /**
     * Method getName -
     * A method to get the name value.
     * @return name
     */
    public String getName() 
    {return name;}

    /**
     * Method setAddress -
     * A method to set the address value.
     * @param address 
     * @return void
     */
    public void setAddress(String address) 
    {this.address = address;}

    /**
     * Method getAddress -
     * A method to get the address value.
     * @return address
     */
    public String getAddress() 
    {return address;}

    /**
     * Method setCity -
     * A method to set the city value.
     * @param address 
     * @return void
     */
    public void setCity(String city) 
    {this.city = city;}

    /**
     * Method getCity -
     * A method to get the city value.
     * @return city
     */
    public String getCity() 
    {return city;}

    /**
     * Method setState -
     * A method to set the state value.
     * @param state 
     * @return void
     */
    public void setState(String state) 
    {this.state = state;}

    /**
     * Method getState -
     * A method to get the state value.
     * @return state
     */
    public String getState() 
    {return state;}

    /**
     * Method setZip -
     * A method to set the zip value.
     * @param zip 
     * @return void
     */
    public void setZip(String zip) 
    {this.zip = zip;}

    /**
     * Method getZip -
     * A method to get the zip value.
     * @return zip
     */
    public String getZip() 
    {return zip;}

    /**
     * Method setArrivalTime -
     * A method to set the arrivalTime value.
     * @param arrivalTime 
     * @return void
     */
    public void setArrivalTime(String arrivalTime) 
    {this.arrivalTime = arrivalTime;}

    /**
     * Method getArrivalTime -
     * A method to get the arrivalTime value.
     * @return arrivalTime
     */
    public String getArrivalTime()
    {return arrivalTime;}

    /**
     * A custom toString method. Not used.
     * @return 
     */
    @Override
    public String toString()
    {
        return this.parcelID + "," + this.name + "," + this.address + "," + 
                this.city + "," + this.state + "," + this.zip + "," + this.arrivalTime; 
    }
}
