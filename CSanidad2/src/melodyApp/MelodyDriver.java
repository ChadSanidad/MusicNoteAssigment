package melodyApp;
/**
 * Class MelodyDriver: will run the melody class.
 * 
 * Usage: java -jar MelodyDriver.jar frequency
 * 
 * It is important that a frequency is only being accepted in 
 * as an argument.
 * @author 672749
 *
 */
public class MelodyDriver 
{
	public static void main(String[] args)
	{
		Melody m = new Melody();
		m.playNotes(args);
	}

}
