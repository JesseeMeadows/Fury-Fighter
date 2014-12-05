import java.util.Collection;
import java.util.Arrays;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

@RunWith(Parameterized.class)


public class TestPlayerView
{
	private PlayerView playerView;

	@Parameter(value = 0)
	public float rw;

	@Parameter(value = 1)
	public float rh;

	@Before
	public void instantiate()
	{	
		PlayerModel model=  new PlayerModel(new LevelModel(new ModelController(null)));
		playerView = new PlayerView(model);
	}

	@Test
	public void testRender()
	{
		playerView.render(Graphics2DMock.getMockObject(), rw, rh);
	}

	@Test(expected=NullPointerException.class)
	public void testRenderNullPointerException()
	{
		playerView.render(Graphics2DMock.getMockObject(), rw, rh);
	}

	@Test
	public void testGetVisibleViews()
	{
		assertEquals(0, playerView.getVisibleViews().size());
	}
}
