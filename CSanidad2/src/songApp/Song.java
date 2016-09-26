package songApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import exceptions.InvalidNoteException;
import utilities.Note;
import utilities.Piano;

/**
 * Song Class: Will read through a text file, and create a new list
 * of notes for the piano to play. Used to determine where there is a rest,
 * where there is a note and how long the duration of the note is.
 * @author 672749
 *
 */
public class Song 
{
	/**
	 * readFile method: used to go through the file and append it to an arraylist.
	 * Checks for a rest or a note. If it is a rest, sleep for 200 milliseconds.
	 * If there is a note, check for each case of note constructors to determine
	 * what type of note it is.
	 * increased durations for notes are indicated by the "-" sign.
	 * 
	 * If the note cannot be interpreted then it is discarded.
	 * 
	 * @param filename - the file to read the notes from
	 * @return - ArrayList of strings representing the notes in the song.
	 * @throws FileNotFoundException - The file cannot be read
	 * @throws InvalidNoteException - The note cannot be parsed
	 */
	public ArrayList<String> readFile (String filename) throws FileNotFoundException,InvalidNoteException {
		Scanner scanner = new Scanner(new File(filename));
		StringBuffer buffer = new StringBuffer();
		while (scanner.hasNext())
			buffer.append(scanner.nextLine() + ",");
		
		StringTokenizer st = new StringTokenizer(buffer.toString(), ",");
		ArrayList<String> notes = new ArrayList<String>(st.countTokens());
		for (int i = 0; st.hasMoreTokens(); i++) 
		{
			String str = st.nextToken().trim(); 
			if(str.contains("r"))
			{
				//instantly add it to the notes list if it is a rest
				notes.add(str);	
			}
			else
			{
				//going to test for each case of note, and if it is valid, adds it to a list
				//if the note is not valid, display error and discard it
				//continue until the notes list is finished.
				String testString;
				if(str.charAt(str.length() - 1) == '-') // if there is a - remove it for now
				{
					//get the note without the minus					
					testString = str.substring(0, str.length() - 1);
				}
				else
				{
					//get the string to test
					testString = str;
				}
				try
				{
					int intTest =  Integer.parseInt(testString);
					Note n = new Note(intTest);
					notes.add(str);
				}
				catch(InvalidNoteException | NumberFormatException e)
				{
					try
					{
						double doubleTest = Double.parseDouble(testString);
						Note n = new Note(doubleTest);
						notes.add(str);
					}
					catch(InvalidNoteException | NumberFormatException e1)
					{
						try
						{
							Note n = new Note(testString);
							notes.add(str);
						}
						catch(InvalidNoteException | IllegalArgumentException e2 )
						{
							System.out.println("Cannot read note: \"" + str + "\"- ignored ");
						}
					}
				}
			}
			

			
		}
		return notes;
	}
	
	/**
	 * playSong method: creates a new piano to play the song and goes through the arraylist created from the readFile method
	 * and plays each note. The default duration is 200 milliseconds, unless specified by a -, therefore the duration is 400 milliseconds.
	 * 
	 * @param notes - the arraylist of notes to be played by the piano
	 * @throws InvalidNoteException - if a note cannot be interpreted properly.
	 */
	public void playSong (ArrayList<String> notes) throws InvalidNoteException 
	{
		Piano piano = new Piano();       
		String noteGet;
		int time = 0;
		for (int i = 0; i < notes.size(); i++)
		{
			String newNote = notes.get(i);
			if(newNote.contains("r")) // if note is a rest
			{
				if (newNote.charAt(newNote.length() - 1) == '-')
				{
					piano.rest(400);
				}
				else
				{
					piano.rest(200);
				}
			}
			else // if note is a note
			{
				if(newNote.charAt(newNote.length() - 1) == '-') //if there is a hold
				{//remove time modifier
					noteGet = newNote.substring(0,newNote.length() - 1);
					time = 400;
				}
				else
				{
					noteGet = newNote;
					time = 200;
				}
				//reversing the notes list. This time, it takes each note and plays it .
					try
					{
						int intTest =  Integer.parseInt(noteGet);
						Note n = new Note(intTest);
						piano.playNote(n, time);
					}
					catch(InvalidNoteException | IllegalArgumentException e)
					{
						try
						{
							double doubleTest = Double.parseDouble(noteGet);
							System.out.println(doubleTest);
							Note n = new Note(doubleTest);
							piano.playNote(n, time);
						}
						catch(InvalidNoteException | IllegalArgumentException e1)
						{
							try
							{
								Note n = new Note(noteGet);
								piano.playNote(n, time);
							}
							catch(InvalidNoteException | IllegalArgumentException e2)
							{
								System.out.println("Error in playing.");
							}
						}
					}
				}
			}

		}
	}
	
	

