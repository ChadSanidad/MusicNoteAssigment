package utilities;

import java.util.*;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;


/**
 * Piano class: handles the playing of notes in the melody and song classes.
 * Utilizes the java.sound.midi  package to handle sounds.
 * @author 672749
 *
 */
public class Piano 
{
	MidiChannel channel;
	/**
	 * Piano constructor: constructs a new piano class for use.
	 * @throws IllegalArgumentException
	 */
	public Piano () throws IllegalArgumentException {
		Synthesizer synthesizer = null;
    	try {
			synthesizer = MidiSystem.getSynthesizer();
		    synthesizer.open();
		} catch (MidiUnavailableException e1) {

			e1.printStackTrace();
		}

	    javax.sound.midi.Instrument [] instruments = synthesizer.getDefaultSoundbank().getInstruments();
	    
	    if (instruments == null)
			try {
				throw new MidiUnavailableException("MIDI unavailable.");
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			}

	    MidiChannel [] channels = synthesizer.getChannels();
	    int i;
		for (i = 0; i < channels.length; i++)
	    	if (channels[i] != null)
	    		break;
	    
	    if (i < channels.length)
	    	channel = channels[i];
		else
			try {
				throw new MidiUnavailableException();
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			}
	    
		synthesizer.loadInstrument(instruments[30]);
		channel.programChange(1);
	}
	/**
	 * playNote method: will play the note specified, for the amount of time. 
	 * @param n - the note object to be played.
	 * @param time - the duration of the time for the note to be played
	 */
	public void playNote (Note n, int time) 
	{
		channel.noteOn(n.getMIDIAbsoluteNumber(), 127);
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
		channel.noteOff(n.getMIDIAbsoluteNumber());
	}
	
	/**
	 * rest method: will rest for the duration specified.
	 * 
	 * @param time - the time for the note to rest in milliseconds
	 */
	public void rest (int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {}
	}
	
}
