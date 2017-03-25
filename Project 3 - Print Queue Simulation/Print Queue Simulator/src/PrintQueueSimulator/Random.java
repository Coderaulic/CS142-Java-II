package PrintQueueSimulator;

/**
 * A Random class extension for a Client/Server Simulation
 * 
 */
public class Random extends java.util.Random
{
    //instance variables.
    private double mean;
    private double standardDeviation;
    
    //default constructor.
    public Random(double mean)
    {
        this.mean = mean;
        this.standardDeviation = mean;
    }
    
    //Overloaded constructor with 2 parameters passed. 
    //Double of mean and standardDeviation.
    public Random(double mean, double standardDeviation)
    {
        this.mean = mean;
        this.standardDeviation = standardDeviation;
    }
    
    @Override
    /**
    * @return a double random number that is normally distributed with
    * the given mean and standard deviation
    */
    public double nextGaussian()
    {
        double x = super.nextGaussian(); // x = normal(0.0, 1.0)
        return x*standardDeviation + mean;
    }
    
    /**
    * @return a double random number that is exponentially distributed
    * with the given mean
    */
    public double nextExponential()
    {
        return - mean*Math.log(1.0 - nextDouble());
    }
    
    public int intNextExponential()
    {
        return (int)Math.ceil(nextExponential());
    }
}

