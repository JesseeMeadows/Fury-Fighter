import static org.junit.Assert.*;
import org.junit.*;

public class TestSoundManager{
	
	@Before
	public void initialize()
	{
		SoundManager.addSound("HiBoss", "assets/snd/BossIntro.wav");
		SoundManager.addSound("Level", "assets/snd/music.wav");
		SoundManager.addSound("Boss", "assets/snd/BossMain.wav");
		SoundManager.addSound("Shoot", "assets/snd/bullet.wav");
		SoundManager.addSound("Hi", "assets/snd/introjingle.wav");
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
	
	@Test
	public void testSetLooping()
	{
		assertEquals(1, SoundManager.setLooping("meh", true));
		assertEquals(0, SoundManager.setLooping("Shoot", true));
		SoundManager.playSound("Shoot");
		try {
			Thread.sleep(1000);//1000 milliseconds is one second.
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		assertTrue(SoundManager.isPlaying("Shoot"));
		assertFalse(SoundManager.isPlaying("meh"));
	}
	
	@Test
	public void testSetVolume()
	{
		assertEquals(1, SoundManager.setVolume("meh", 50.0f));
		assertEquals(0, SoundManager.setVolume("Hi", 50.0f));
		SoundManager.playSound("Hi");
		try {
			Thread.sleep(1000);//1000 milliseconds is one second.
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		assertEquals(0, SoundManager.setVolume("Hi", 100.0f));
		SoundManager.playSound("Hi");
	}
	
	@Test
	public void testSetPan()
	{
		assertEquals(0, SoundManager.setPan("Hi", 50.0f));
		assertEquals(1, SoundManager.setPan("meh", 50.0f));
	}
}