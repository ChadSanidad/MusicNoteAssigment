package songApp;

import java.io.FileNotFoundException;

import exceptions.InvalidNoteException;

/**
 * SongDriver class: Will play the song specified in the argument.
 * Usage: java -jar SongDriver.jar file
 * 
 * @author 672749
 *
 */
public class SongDriver 
{

	public static void main(String[] args)
	{
		if (args.length < 1 || args.length > 2)
		{
			System.out.println("Error, please type a file as an argument.");
			System.out.print(" Ex. java -jar SongDriver.jar song.txt ");
			System.exit(0);
		}
		Song song = new Song();
		try
		{	
			song.playSong(song.readFile(args[0]));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found");
			System.exit(0);
		} catch (InvalidNoteException e) {
			System.out.println("Error reading note.");
		}
	}
}
