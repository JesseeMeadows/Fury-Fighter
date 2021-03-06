
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Box;
import org.junit.*;
import static org.junit.Assert.*;
import org.json.simple.JSONObject;

public class TestPlayerModel {
	private PlayerModel playerModel;

	@Before
	public void initialize()
	{
		playerModel = new PlayerModel(new LevelModel(new ViewController(new ModelController(null)).getModelController()));
	}

	@Test
	public void testDeath()
	{
		playerModel.death();
	}


	@Test
	public void testGetBoundingBox()
	{
		Rectangle result = playerModel.getBoundingBox();

		assertNotNull(result);
	}




	@Test
	public void testGetBulletList()
	{
		assertNotNull(playerModel.getBulletList()); // This is an array lists and should never be null
	}

//Turns out these are private methods so not sure how to test properly.
/*
	@Test
	public void testFireBasic()
		{
			// Was not really sure how to test this. Fire adds a new bullet to the list so I just check for that.
		PlayerModel playerModel = new PlayerModel(new LevelModel(new ModelController(null)));
		int size=playerModel.getBulletList().size();
		playerModel.fireBasic();
            assertTrue(playerModel.getBulletList().size()>size);
	}

	@Test
	public void testFireRing()
		{
			// Was not really sure how to test this. Fire adds a new bullet to the list so I just check for that.
		PlayerModel playerModel = new PlayerModel(new LevelModel(new ModelController(null)));
		int size=playerModel.getBulletList().size();
		playerModel.fireRing();
            assertTrue(playerModel.getBulletList().size()>size);
	}

	@Test
	public void testFireMissle()
		{
			// Was not really sure how to test this. Fire adds a new bullet to the list so I just check for that.
		PlayerModel playerModel = new PlayerModel(new LevelModel(new ModelController(null)));
		int size=playerModel.getBulletList().size();
		playerModel.fireMissle();
            assertTrue(playerModel.getBulletList().size()>size);
	}


	@Test
	public void testFireLaser()
		{
			// Was not really sure how to test this. Fire adds a new bullet to the list so I just check for that.
		PlayerModel playerModel = new PlayerModel(new LevelModel(new ModelController(null)));
		int size=playerModel.getBulletList().size();
		playerModel.fireLaser();
            assertTrue(playerModel.getBulletList().size()>size);
	}
*/
	@Test
	public void testGetPickup()
	{
		int	temp=playerModel.getRingLevel();
		playerModel.getPickup("ring");
		assertTrue((temp+1)==playerModel.getRingLevel()); // Since its suppposed to give you +1

		temp=playerModel.getMissleLevel();
		playerModel.getPickup("missile");
		assertTrue((temp+1)==playerModel.getMissleLevel()); // Since its suppposed to give you +1


		temp=playerModel.getLaserLevel();
		playerModel.getPickup("laser");
		assertTrue((temp+1)==playerModel.getLaserLevel()); // Since its suppposed to give you +1


		temp=playerModel.getDefensePodLevel();
		playerModel.getPickup("defense_pod");
		assertTrue((temp+1)==playerModel.getDefensePodLevel()); // Since its suppposed to give you +1



	}

	@Test
	public void testGetPlayerImage()
	{
		BufferedImage result = playerModel.getPlayerImage();

		assertNotNull(result);
	}


	@Test
	public void testGetVisibleModels()
	{
		HashMap<String, Model> result = playerModel.getVisibleModels();

		assertNotNull(result);
	}


	@Test
	public void testKeyDownResponse()
	{
		KeyEvent e = new KeyEvent(Box.createGlue(), 1, 1L, 1, 1);

		playerModel.keyDownResponse(e);
	}


	@Test
	public void testKeyUpResponse()
	{
		KeyEvent e = new KeyEvent(Box.createGlue(), 1, 1L, 1, 1);

		playerModel.keyUpResponse(e);
	}

	@Test
	public void testOnTiles()
	{
		ArrayList<Integer> result = playerModel.onTiles();

		//assertTrue(result.size()>0);// If there is no tiles then something went wrong.
		assertNotNull(result);
	}
}
