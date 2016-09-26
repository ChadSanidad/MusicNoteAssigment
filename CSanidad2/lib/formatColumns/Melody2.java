
package formatColumns;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import exceptions.InvalidNoteException;
import utilities.Note;
import utilities.Piano;


public class Melody2 
{
	
	public static void main (String [] args) 
	{
		Melody2 m = new Melody2();
		m.playNote(args);
		System.exit(0);
	}
	
	public void playNote(String [] args)
	{
		Piano piano = new Piano();
      double str = Double.parseDouble(args[0]);
		try
		{
			Note start = new Note(str);
	      for(Note n = new Note(start); (n.compareTo(start)!=12); n.modifyNoteBySemitones(1))
	      {
	      	piano.playNote(n, 200);
     	  }
		} 
		catch (InvalidNoteException e)
		{
			System.out.println("Error note must be entered as a frequency number (0-13000)");
		}       
	}
}

	

