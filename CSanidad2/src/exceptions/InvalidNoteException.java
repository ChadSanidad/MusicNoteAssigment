package exceptions;

/**
 * InvalidNoteException: Will be raised whenever an error occurs
 * within the creation and operation of a note.
 * @author 672749
 *
 */
public class InvalidNoteException extends Exception
{
	/**
	 *Default InvalidNoteException
	 */
	public InvalidNoteException()
	{
		
	}

	/**
	 * Will display a specific message that caused the error
	 * when this is raised. 
	 * @param message error message specific to cause of error.
	 */
	public InvalidNoteException(String message)
	{
		super(message);
	}
}
