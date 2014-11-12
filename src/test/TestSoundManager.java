import static org.junit.Assert.*;
import org.junit.*;

public class TestSoundManager{
	
	@Before
	public void initialize()
	{
		SoundManager.addSound("HiBoss", "snd/BossIntro.wav");
	}
	
	@Test
	public void testGet(){
		assertTrue(SoundManager.get() instanceof SoundManager);
	}
	
	@Test
	public void testGetMySound()
	{
		assertTrue(SoundManager.getMySound("HiBoss") instanceof MySound);
		assertTrue(SoundManager.getMySound("meh") == null);
	}
	
	@Test
	public void testPlaySound()
	{
		assertFalse(SoundManager.isPlaying("meh"));
		assertFalse(SoundManager.isPlaying("HiBoss"));
		assertEquals(1,SoundManager.playSound("meh"));
		assertEquals(0,SoundManager.playSound("HiBoss"));
		assertFalse(SoundManager.isPlaying("meh"));
		assertTrue(SoundManager.isPlaying("HiBoss"));
	}
	
	@Test
	public void testPauseSound()
	{
		SoundManager.playSound("meh");
		SoundManager.playSound("HiBoss");
		assertFalse(SoundManager.isPlaying("meh"));
		assertTrue(SoundManager.isPlaying("HiBoss"));
		assertEquals(1,SoundManager.pauseSound("meh"));
		assertEquals(0,SoundManager.pauseSound("HiBoss"));
		assertFalse(SoundManager.isPlaying("meh"));
		assertFalse(SoundManager.isPlaying("HiBoss"));
	}
	
	@Test
	public void testStopSound()
	{
		SoundManager.playSound("meh");
		SoundManager.playSound("HiBoss");
		assertFalse(SoundManager.isPlaying("meh"));
		assertTrue(SoundManager.isPlaying("HiBoss"));
		assertEquals(1,SoundManager.stopSound("meh"));
		assertEquals(0,SoundManager.stopSound("HiBoss"));
		assertFalse(SoundManager.isPlaying("meh"));
		assertFalse(SoundManager.isPlaying("HiBoss"));
	}
}