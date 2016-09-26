package utilities;
import exceptions.InvalidNoteException;
import utilities.*;

public class Note extends NoteADT
{
	/**
	 * Note constructor -double
	 * Takes in a double frequency to create the note, see ADT for more information
	 * @param frequency - the frequency for the note to be played at.
	 * @throws InvalidNoteException - the note cannot be interpreted properly
	 */
	public Note(double frequency) throws InvalidNoteException
	{
		super(frequency);
	}

	/**
	 * Note constructor -semitones
	 * Takes in the number of semitones above or below concert pitch
	 *  to create the note, see ADT for more information
	 * @param semitones - the frequency for the note to be played at.
	 * @throws InvalidNoteException -  the note cannot be interpreted properly
	 */
	public Note(int semitones) throws InvalidNoteException
	{
		super(semitones);
	}
	/**
	 * Note constructor -semitones
	 * Takes in the number of semitones above or below concert pitch
	 *  to create the note, see ADT for more information
	 * @param frequency - the frequency for the note to be played at.
	 * @throws InvalidNoteException - the note cannot be interpreted properly
	 */
	public Note(String strNote) throws InvalidNoteException
	{
		super(strNote);
	}

	/**
	 * Note constructor, built specifically for the melody class,
	 * takes in a note and the halfsteps for the note.
	 * 
	 * @param n - the new note to take in 
	 * @throws InvalidNoteException - if the note cannot be interpreted
	 */
	public Note(Note n) throws InvalidNoteException
	{
		this(n.getHalfSteps());
	}
	
	  /**
	   * Returns the frequency of this note in cycles per second- Hertz (Hz)
	   * 
	   * 
	   * @return double The frequency in Hz.
	   */
	public double getFrequencyInHz()
	{	
		return Math.pow(SEMI_TONE_INCREASE_IN_PITCH,
					midiNoteValue - MIDI_CONCERT_PITCH)
					* HZ_CONCERT_PITCH;
	}
	
  /**
   * Returns the frequency of this note in semitones (half steps) above or below
   * the concert pitch (440Hz).
   * 
   * @return int The frequency in Semitones or half steps
   */
	public int getHalfSteps()
	{
		return (int) (getMIDIAbsoluteNumber() - MIDI_CONCERT_PITCH);
	}

	  /**
	   * Returns the frequency of this note as a MIDI absolute Number.
	   * 
	   *  
	   * @return int The frequency MIDI absolute Number
	   */
	public int getMIDIAbsoluteNumber()
	{
		return Math.abs(midiNoteValue);
	}


   /**
    * Method compares if this note is 12 semitones above or 
    * 12 semitones below the other note, then the notes form an octave.
    * 
    * 
    * @param note The note that can form an Octave
    * @return boolean true if they do form an octave
    */	
	public boolean formOctave(NoteADT note)
	{
		return Math.abs(note.getMIDIAbsoluteNumber() - this.midiNoteValue) == 12;
	}

   /**
    * Raise or lower the pitch by any number of semitones.
    * 
    * 
    * @param numberOfSemitones Change int used to calculate the new note
    */
	public void modifyNoteBySemitones(int numberOfSemitones)
	{
		midiNoteValue = midiNoteValue + numberOfSemitones;
	}

   /**
    * The compareTo method will be implemented by the Comparable interface
    * it will adhere to the Comparable interface contract referenced in
    * the Java API - java.lang.*; and will have the following signature.
    * public int comparteTo(NoteADT note);.
    */
	public int compareTo(NoteADT note)
	{	
		if(note == null)
			try
			{
				throw new InvalidNoteException();
			} catch (InvalidNoteException e)
			{
				e.printStackTrace();
			}
		
		if(getMIDIAbsoluteNumber() > note.getMIDIAbsoluteNumber())
			return getMIDIAbsoluteNumber() - note.getMIDIAbsoluteNumber();
		
		if(getMIDIAbsoluteNumber() < note.getMIDIAbsoluteNumber())
			return getMIDIAbsoluteNumber() - note.getMIDIAbsoluteNumber();
		
		else
			return 0; 
	}
	
}
