package exceptions;

public class ExerciseConfilctException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExerciseConfilctException() {
    	super("this Exercise can't be created, because this is another exercise for this user conflicts");
    }

    public ExerciseConfilctException(String message)
    {
       super(message);
    }
	
}
