package PrintQueueSimulator;
/**
 * This Client class has all the property of the jobs
 * @author siqi zhang
 */

public class Client 
{
    //Instance variables:
    static public final int MEAN_JOB_SIZE = 100;
    public int id,jobSize,tArrived,tBegin,tEnded;
    
    Random randomNumber = new Random(MEAN_JOB_SIZE);
    /**
     * default constructor, require to enter the job arrival time
     * @param time 
     */
    public Client(int time)//enter the arrival time(second)
    {   
        jobSize = randomNumber.intNextExponential();
        tArrived=time;
    }
    /**
     * Method setId-
     * this method will set the id to the entry
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Method getArrivalTime-
     * This method wil return the job arrival time
     * @return tArrived
     */
    public int getArrivalTime() 
    {
        return tArrived;
    }
     /**
     * Method getJobSize-
     * This method wil return the job size
     * @return jobSize
     */
    public int getJobSize()
    {   
        return jobSize;
    }
    /**
     * Method:getServiceTime-
     * this method will return the time that job spends in the printer
     * @return tEnded - tBegin
     */
    public int getServiceTime()
    {
         return tEnded - tBegin;
    }
    /**
     * Method:getWaitingTime-
     * this method will return the time that job spends waiting in queue
     * @return tEnded - tBegin
     */
    public int getWaitingTime()
    {
        return tBegin - tArrived;
    }
     /**
     * Method getEndTime-
     * This method will return the time when job ends
     * @return tEnded
     */
    public int getEndTime() {
        return tEnded;
    }
    /**
     * Method beginService-
     * This method will call the server.beginServing() and 
     * assign current Client to that server.
     * @param server
     * @param time 
     */       
    public void beginService( Server server, int time)// enter the begin time
    {     
          server.beginServing(this, time);
          tBegin = time;
          tEnded = server.getTimeServiceEnds();
//          System.out.println("Printer " + server.getPrinter() + " begins job #" + id +
//                " at "+ time);
          tBegin = time;
    }
    /**
     * Method endService-
     * This method will get the time when job ends 
     * @param server
     * @param time 
     */  
    public void endService(Server server ,int time)//enter the begin time 
    {   
        tEnded = tBegin + server.serviceTime;
//        System.out.println("Printer " + server.getPrinter() + " ends job #" + id +
//                " at "+ tEnded);
    }
    /**
     * Method getId-
     * This method will return the current job id
     * @return id
     */
    public int getId() {
        return id;
    }
    /**
     * Method: toString-
     * Overriding the toString method
     * @return 
     */
    @Override
    public String toString()
    {
        return this.id + ", " + this.jobSize + ", " + this.tArrived + ", " + 
                this.tBegin + ", " + this.tEnded;
    }
}