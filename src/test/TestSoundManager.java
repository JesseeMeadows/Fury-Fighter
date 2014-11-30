import static org.junit.Assert.*;
import org.junit.*;

public class TestSoundManager{
	
	@Before
	public void initialize()
	{
		SoundManager.addSound("HiBoss", "assets/snd/BossIntro.wav");
		SoundManager.addSound("Level", "assets/snd/music.wav");
		SoundManager.addSound("Boss", "assets/snd/BossMain.wav");
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
		assertFalse(SoundManager.isPlaying("Level"));
		assertEquals(1,SoundManager.playSound("meh"));
		assertEquals(0,SoundManager.playSound("Level"));
		assertFalse(SoundManager.isPlaying("meh"));
		try {
			Thread.sleep(1000);//1000 milliseconds is one second.
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		assertTrue(SoundManager.isPlaying("Level"));
	}
	
	@Test
	public void testPauseSound()
	{
		SoundManager.playSound("meh");
		SoundManager.playSound("HiBoss");
		try {
			Thread.sleep(1000);//1000 milliseconds is one second.
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		assertFalse(SoundManager.isPlaying("meh"));
		assertTrue(SoundManager.isPlaying("HiBoss"));
		assertEquals(1,SoundManager.pauseSound("meh"));
		assertEquals(0,SoundManager.pauseSound("HiBoss"));
		assertFalse(SoundManager.isPlaying("meh"));
		try {
			Thread.sleep(1000);//1000 milliseconds is one second.
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		assertFalse(SoundManager.isPlaying("HiBoss"));
	}
	
	@Test
	public void testStopSound()
	{
		SoundManager.playSound("meh");
		SoundManager.playSound("Boss");
		try {
			Thread.sleep(1000);//1000 milliseconds is one second.
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		assertFalse(SoundManager.isPlaying("meh"));
		assertTrue(SoundManager.isPlaying("Boss"));
		assertEquals(1,SoundManager.stopSound("meh"));
		assertEquals(0,SoundManager.stopSound("Boss"));
		assertFalse(SoundManager.isPlaying("meh"));
		try {
			Thread.sleep(1000);//1000 milliseconds is one second.
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		assertFalse(SoundManager.isPlaying("Boss"));
	}
}