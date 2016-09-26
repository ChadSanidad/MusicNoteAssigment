

package junit;

import exceptions.InvalidNoteException;
import utilities.Note;

import static org.junit.Assert.*;

import org.junit.*;



public class TestNote {
	
	/* Fields */
	
	private Note note1, note2, note3;
	private double delta = 0.0;
	
	/* END Fields */
	
	
	/* Set Up */
	
	@Before
	public void setUp () throws InvalidNoteException 
	{
	}
	
	/* END Set Up */
	
	
	/* Tear Down */
	
	@After
	public void tearDown () 
	{

	}
	
	
	@Test
	public void testNoteInt() 
	{
		try
		{
			int test = 5;
			Note note = new Note(test);
			//System.out.println(note.getMIDIAbsoluteNumber());
		}
		catch(InvalidNoteException e)
		{
			fail("Raised an invalid note");
		}
	}
	
	@Test
	public void testNoteDouble() 
	{
		try
		{
			double value = 221.0;
			Note note = new Note(value);
		}
		catch(InvalidNoteException e)
		{
			fail("Raise and invalid note");
		}
	}

	@Test
	public void testNoteDoubleDecimals() 
	{
		try
		{
			double value = 221.89797897890;
			Note note = new Note(value);
			fail("Decimal point greater than 2");
		}
		catch(InvalidNoteException e)
		{

		}
	}
	
	@Test
	public void testNoteString() 
	{
		try
		{
			String n = "G4";
			Note note = new Note(n);
		}
		catch(InvalidNoteException e)
		{
			fail("Raised an invalid note");
		}
		
		try
		{
			String n = "G4#";
			Note note = new Note(n);
		}
		catch(InvalidNoteException e)
		{
			fail("Raised an invalid note");
		}
	}

	@Test
	public void testNoteStringFail() 
	{
		try
		{
			String n = "K";
			Note note = new Note(n);
			fail("Should have raised an error");
		}
		catch(InvalidNoteException | IllegalArgumentException e)
		{
		}
	}
	@Test
	public void testGetMidiAbsoluteValueInt() throws InvalidNoteException
	{
		Note note = new Note(1);
		assertEquals(70,note.getMIDIAbsoluteNumber(),delta);
	}
	
	@Test
	public void testGetMidiAbsoluteValueDouble() throws InvalidNoteException
	{
		Note note = new Note(440.0);
		assertEquals(69,note.getMIDIAbsoluteNumber(),delta);
	}
	
	@Test 
	public void testGetMidiAbsoluteValueString() throws InvalidNoteException
	{
		Note note = new Note("A3");
		assertEquals(57,note.getMIDIAbsoluteNumber(),delta);
	}
	
	@Test
	public void testGetHalfSteps() throws InvalidNoteException
	{
		Note note = new Note(1);
		assertEquals(1,note.getHalfSteps(),delta);
		
		Note note2 = new Note(440.0);
		assertEquals(0,note2.getHalfSteps(),delta);
	}
	
	@Test
	public void testUpperNoteErrorInt()
	{
		try
		{
			Note note = new Note(256);
			fail("Should raise an invalid note exception");
		}
		catch(InvalidNoteException e)
		{
			
		}
	}
	
	public void testUpperNoteErrorDouble()
	{	
		try
		{
			Note note = new Note(140000);
			fail("Should raise an invalid note exception");
		}
		catch(InvalidNoteException e)
		{
			
		}
		try
		{
			Note note = new Note("A27");
			fail("Should raise an invalid note exception");
		}
		catch(InvalidNoteException e)
		{
		}
	}
	
	@Test
	public void testLowerNoteErrorInt()
	{
		try
		{
			Note note = new Note(-100);
			fail("Should raise an invalid note exception");
		}
		catch(InvalidNoteException e)
		{
			
		}
	}
	@Test
	public void testLowerNoteErrorDouble()
	{
		try
		{
			Note note = new Note(-5.0);
			fail("Should raise an invalid note exception");
		}
		catch(InvalidNoteException e)
		{
		}
	}
	
	@Test
	public void testLowerNoteErrorString()
	{
		try
		{
			Note note = new Note("A-9");
			fail("Should raise an invalid note exception");
		}
		catch(InvalidNoteException e)
		{
		}
	}
	
	@Test
	public void formOctave() throws InvalidNoteException
	{
		Note n = new Note(0);
		Note n2 = new Note(12);
		assertTrue(n.formOctave(n2));
		

	}
	@Test
	public void formOctaveFalse()throws InvalidNoteException
	{
		Note n3 = new Note(0);
		Note n4 = new Note(27);
		assertFalse(n3.formOctave(n4));
	}
	
	@Test
	public void testIncreaseOctave() throws InvalidNoteException
	{
		Note n3 = new Note(0);
		n3.modifyNoteBySemitones(3);
		assertEquals(72,n3.getMIDIAbsoluteNumber(),delta);
	}
	
	@Test
	public void testDecreaseOctave() throws InvalidNoteException
	{
		Note n3 = new Note(0);
		n3.modifyNoteBySemitones(-3);
		assertEquals(66,n3.getMIDIAbsoluteNumber(),delta);
	}
}

