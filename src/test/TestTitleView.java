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

public class TestTitleView
{
	private TitleView titleView;

	@Parameter(value = 0)
	public float rw;

	@Parameter(value = 1)
	public float rh;

	@Parameters
	public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][] {
        		{0.0f, 0.0f}, {512.0f, 512.0f}, {96.0f, 333.0f}
        });
    }

	@Before
	public void instantiate()
	{
		titleView = new TitleView(new ViewController(new ModelController(mock(TitleModel.class))));
	}

	@Test
	public void testRender()
	{
		titleView.renderWithImages(Graphics2DMock.getMockObject(), rw, rh, mock(BufferedImage.class), mock(BufferedImage.class));
	}

	@Test(expected=NullPointerException.class)
	public void testRenderNullPointerException()
	{
		titleView.renderWithImages(Graphics2DMock.getMockObject(), rw, rh, null, null);
	}

	@Test
	public void testGetVisibleViews()
	{
		assertEquals(0, titleView.getVisibleViews().size());
	}
}
