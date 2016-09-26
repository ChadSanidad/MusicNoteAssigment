
package melodyApp;


import exceptions.InvalidNoteException;
import utilities.Note;
import utilities.Piano;

/**
 *Melody class: plays a note upwards until an octave has been played.
 *Uses the compareTo method to handle the octave, and takes
 *the piano class play the octave.
 * 
 * @author 672749
 *
 */
public class Melody 
{
	public void playNotes(String[] args)
	{
		   Piano piano = new Piano();
	       double str = Double.parseDouble(args[0]);
			try
			{
			  Note start = new Note(str);
		      for(Note n = new Note(start); (n.compareTo(start)!=12); n.modifyNoteBySemitones(1))
		      {
		      	piano.playNote(n, 400);
	     	  }
			} 
			catch (InvalidNoteException e)
			{
				System.out.println("Error note must be entered as a frequency number (0-13000)");
			}     
	}
}

	

