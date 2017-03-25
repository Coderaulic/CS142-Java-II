package PrintQueueSimulator;

/**
 * This class has all the property of printers(Server)
 * Siqi Zhang
 */
public class Server
{   
    //constructing a random object
    Random randomNumber = new Random(MEAN_SERVICE_RATE,STANDARD_DEVIATION_MEAN_SERVICE_RATE);
    //instance variables
    static final double MEAN_SERVICE_RATE = 100.0;
    static final double STANDARD_DEVIATION_MEAN_SERVICE_RATE = 20.0;
    static final double STANDARD_DEVIATION_ACTUAL_SERVICE_RATE = 10.0;
    static final int DENOMINTOR = 100;
    
    public char id;//printerID
    public int meanServiceRate = (int)randomNumber.nextGaussian();//mean rate of the printer 
    public double serviceRate;
    public int timeServiceEnds;
    public int serviceTime;
    private boolean isFree;
    public Client currentClient;
    /**
     * default constructor
     */
    public Server()
    {   
        id='A';
        serviceRate = 0;
        timeServiceEnds = 0;
        isFree = true;
    }
    /**
     * Method:getId-
     * This method will return the printer ID
     * @return id
     */
    public char getId()
    {
        return id;
    }
    /**
     * Method:getPrinter-
     * This method will return the printer information
     * @return id+"("+meanServiceRate+"% , "+serviceRate+"%)"
     */
    public String getPrinter()
    {   
        return id+"("+meanServiceRate+"% , "+serviceRate+"%)";
    }
    /**
     * Method:setId-
     * This method will set the id to the entry
     * @param id 
     */
    public void setId(char id) {
        this.id = id;
    }
    /**
     * Method:getServiceRate-
     * This method will return the service rate
     * @return serviceRate
     */
    public double getServiceRate() {
        return serviceRate;
    }
    /**
     * Method:getMeanServiceRate-
     * This method will return the mean service rate
     * @return meanServiceRate
     */
    public int getMeanServiceRate() {
        return meanServiceRate;
    }
    /**
     * Method: getClient-
     * This method returns the Client(job) that this printer is processing
     * @return currentClient
     */
    public Client getClient()
    {       
      return currentClient;
    }
    /**
     * Method:beginServing-
     * This method will calculate the service time, generate random service rate
     * and add up the timeServiceEnds
     * @param client
     * @param time 
     */
    public void beginServing(Client client, int time)
    {   
        double denomintor;
        currentClient = client;
        randomNumber = new Random(meanServiceRate,STANDARD_DEVIATION_ACTUAL_SERVICE_RATE);
        serviceRate = (int)randomNumber.nextGaussian();//actuall rate of the printer  
        serviceTime = (int)Math.ceil(client.getJobSize()/(serviceRate/DENOMINTOR));
        timeServiceEnds = time + serviceTime;
        isFree = false;
    } 
    /**
     * Method:endServing-
     * set timeServiceEnds and set printer.isFree to true
     * @param time 
     */
    public void endServing(int time)
    {  
       timeServiceEnds += serviceTime;
       isFree = true;
    }
    /**
     * Method:getTimeServiceStarts-
     * return timeServiceEnds - serviceTime
     * @return timeServiceEnds
     */
    public int getTimeServiceStarts()
    {
        return timeServiceEnds - serviceTime;
    }
    /**
     * Method:getTimeServiceEnds-
     * return the timeServiceEnds
     * @return timeServiceEnds
     */
    public int getTimeServiceEnds()
    {
        return timeServiceEnds;
    }  
    /**
     * Method: isFree-
     * return true if the printer is free, else return false
     * @return true/false
     */
    public boolean isFree()
    {   
        if (isFree == true)
        return true;
        else
        return false;
    }
    /**
     * Method:toString-
     * Overiding toString method
     * @return meanServiceRate + ", " + serviceRate + ", " + timeServiceEnds
     */
    public String toString()
    {
        return meanServiceRate + ", " + serviceRate + ", " + timeServiceEnds;
    }
}