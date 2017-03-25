package PrintQueueSimulator;

// EmptyListException.java
// Class EmptyListException definition
public class EmptyListException extends RuntimeException
{
    public EmptyListException( String name )
    {
        super( "The " + name + " is empty" );
    }
}