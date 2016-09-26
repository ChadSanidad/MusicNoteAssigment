package utilities;

import java.text.DecimalFormat;
import java.util.StringTokenizer;

import exceptions.*;
/**
  *
  *
  * This is the contract specification for a musical pitch or note.
  * A pitch can be completely specified as one of the following:
  * 1. A number of cycles per second or frequency.
  * 2. The number of half steps above a commonly agreed upon pitch
  *    (concert pitch=440Hz = A = MIDI69) or
  * 3. The common music note name (C, D, E, F, G, A, B) with the
  *    the suffix indicating the octave number [-1,9] and an additional
  *    suffix prefix '#' for sharp notes and 'b' for flat notes or
  * 4. The MIDI absolute note number [0,127] where 60 is middle C.
  *
  * Higher pitches have higher frequencies. Two pitches are an octave apart
  * (12 semitones) if one frequency is twice the other. A semitone is
  * aproximately an increase in pitch of 1.06 times higher.
  */

public abstract class NoteADT implements Comparable<NoteADT>
{

	protected int midiNoteValue = -1;

  /**
   * A semitone is aproximately an increase in pitch of 1.06 times higher.
   */
  public static final double SEMI_TONE_INCREASE_IN_PITCH = Math.pow(2.0,1.0/12.0);
  /**
   * The agreed upon pitch "modern concert pitch"
   */
  public static final double HZ_CONCERT_PITCH = 440.0; //Hz
  public static final int    MIDI_CONCERT_PITCH = 69;
  
  /**
   * The high and low limits on the range of midi numbers.
   */
  public static final int    LOW_MIDI_ABSOLUTE_NUMBER = 0;
  public static final int    HIGH_MIDI_ABSOLUTE_NUMBER = 127;

  
  /**
   * The constructors for a note accept a frequency as a double for Hz or
   * a number of semitones as an int above or below the concert pitch (440Hz)
   * or a music note as a String such as C#4 
   * Imperfect frequencies are tuned to the nearest half pitch
   * Notes that are outside the range of 0 - 127 are invalid
   * notes and the InvalidNoteException is raised.
   */

	public NoteADT(double frequency) throws InvalidNoteException
	{
		String[] splitter = Double.toString(frequency).split("\\.");
		splitter[0].length();   // Before Decimal Count
		int decimalLength = splitter[1].length();  // After Decimal Count
		if (decimalLength > 2)
		{
			throw new InvalidNoteException("Decimal frequency too long");
		}
		
		midiNoteValue = (int) (Math.log(frequency/HZ_CONCERT_PITCH)
				/Math.log(SEMI_TONE_INCREASE_IN_PITCH))
				+ MIDI_CONCERT_PITCH;
		if (frequency < 0 ||frequency > 13000)
			throw new InvalidNoteException("Frequency out of range");

		NoteValueCheck(midiNoteValue);
	}

	public NoteADT(int semitones) throws InvalidNoteException
	{
		midiNoteValue = (int) (MIDI_CONCERT_PITCH + semitones);
		
		NoteValueCheck(midiNoteValue);
	}


	public NoteADT(String strNote) throws InvalidNoteException
	{
		int letter = 0;
		int suffix = 0;
		int octave = 0;
		//parse note 
	
			if(strNote.matches(".*[A-Za-z].*"))
			{
				char note = strNote.toUpperCase().charAt(0);
				switch(note)
				{
					case 'A':
						letter = 9;
						break;
					case 'B':
						letter = 11;
						break;
					case 'C':
						letter = 0;
						break;
					case 'D':
						letter = 2;
						break;
					case 'E':
						letter = 4;
						break;
					case 'F':
						letter = 5;
						break;
					case 'G':
						letter = 7;
						break;
					default:
						throw new InvalidNoteException("Note error letter:");
				}	
			}
			else
			{
				throw new IllegalArgumentException("Note could not be read.");
			}
		
			if(strNote.length() > 1)
			{
				strNote = strNote.substring(1); // remove the letter value
			}
			for (int i = -1; i <= 9; i++) 
			{
				String si = String.valueOf(i);
				int index = strNote.indexOf(si);
				if (index != -1) 
				{
					String s = strNote.substring(0, index) + strNote.substring(index + si.length());
					for (int j = 0; j < s.length(); j++)
							if (Character.isDigit(s.charAt(j)) || s.charAt(j) == '-')
								throw new InvalidNoteException("The octave is invalid.");
					octave = i;
				}
			}
			// get suffix, if the note contains b, then its flat, therefore value = -1
			// if the note contains #, then its sharp, therefore value = +1
			if (strNote.contains("b"))
			{
				suffix = -1;
			}
			
			if (strNote.contains("#"))
			{
				suffix = 1;
			}
			int tempNote = (octave + 1) * 12 + letter + suffix;
			if (tempNote < LOW_MIDI_ABSOLUTE_NUMBER 
					|| tempNote > HIGH_MIDI_ABSOLUTE_NUMBER)
				throw new InvalidNoteException("The Note value is out of range.");
			midiNoteValue = tempNote;
	}

		//only need the rest if its a rest
	

  /**
   * Returns the frequency of this note in cycles per second- Hertz (Hz)
   * 
   * Preconditions: A valid internal reference number exists for a note
   * and the numbers range from 0 (C-1) to 127 (G9).
   * 
   * Postconditions: The internal reference number is successfully converted
   * to a frequency in hertz and returned to the calling function.
   * 
   * @return double The frequency in Hz.
   */
   public abstract double getFrequencyInHz();

  /**
   * Returns the frequency of this note in semitones (half steps) above or below
   * the concert pitch (440Hz).
   * 
   * Preconditions: A valid internal reference number exists for a note
   * and the numbers range from 0 (C-1) to 127 (G9).
   * 
   * Postconditions: The internal reference number is successfully converted
   * to number of half steps or semitones above or below concert pitch (69)
   * and returned to the calling function.
   * 
   * @return int The frequency in Semitones or half steps
   */
   public abstract int getHalfSteps();

  /**
   * Returns the frequency of this note as a MIDI absolute Number.
   * 
   * Preconditions: A valid internal reference number exists for a note
   * and the numbers range from 0 (C-1) to 127 (G9).
   * 
   * Postconditions: The internal reference number is successfully returned
   * as the MIDI absolute Number to the calling function.
   *  
   * @return int The frequency MIDI absolute Number
   */
   public abstract int getMIDIAbsoluteNumber();

   /**
    * Method compares if this note is 12 semitones above or 
    * 12 semitones below the other note, then the notes form an octave.
    * 
    * Preconditions: A valid internal reference number exists and the note
    * passed into the method is a valid Note.
    * 
    * Postconditions: An octave is formed if the note passed in is exactly
    * 12 semitones above or below the internal reference MIDI absolute number.
    * If the difference is + or - 12 semitones then a boolean true is returned
    * otherwise a boolean false is returned.
    * 
    * @param note The note that can form an Octave
    * @return boolean true if they do form an octave
    */
   public abstract boolean formOctave(NoteADT note);

   /**
    * Raise or lower the pitch by any number of semitones.
    * 
    * Preconditions: A valid internal reference number exists for a note
    * and the numbers range from 0 (C-1) to 127 (G9).
    * 
    * Postconditions: The internal reference MIDI absolute number is
    * increased or decreased by the amount of the semitones passed into
    * the method.
    * 
    * @param numberOfSemitones Change int used to calculate the new note
    */
    public abstract void modifyNoteBySemitones(int numberOfSemitones);

   /**
    * The compareTo method will be implemented by the Comparable interface
    * it will adhere to the Comparable interface contract referenced in
    * the Java API - java.lang.*; and will have the following signature.
    * public int comparteTo(NoteADT note);.
    */
    public abstract int compareTo(NoteADT note);
    

	private void NoteValueCheck(int midiNoteValue) throws InvalidNoteException
	{
		if(midiNoteValue < LOW_MIDI_ABSOLUTE_NUMBER || midiNoteValue > HIGH_MIDI_ABSOLUTE_NUMBER)
		{
			throw new InvalidNoteException("Error creating the note");	
		}

	}
}