import static org.mockito.Mockito.*;
import java.awt.Graphics2D;

public class Graphics2DMock
{
	public static Graphics2D getMockObject()
	{
		Graphics2D mock = mock(Graphics2D.class);
		return mock;
	}
}
