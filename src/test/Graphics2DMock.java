import static org.mockito.Mockito.*;
import org.mockito.ArgumentMatcher;
import java.awt.Graphics2D;
import java.text.AttributedCharacterIterator;

public class Graphics2DMock
{
	private static class IsNullString extends ArgumentMatcher<String>
	{
		public boolean matches(Object a)
		{
			return a == null;
		}
	}

	private static class IsNullAttributedCharacterIterator extends ArgumentMatcher<AttributedCharacterIterator>
	{
		public boolean matches(Object a)
		{
			return a == null;
		}
	}

	public static Graphics2D getMockObject()
	{
		Graphics2D mock = mock(Graphics2D.class);

		/* I spent 6 hours trying to mock these two, its futile because Mockito gives us cryptic errors. */
		/* These just check to make sure that the first argument to drawString isn't null. Don't pass a null value into drawString. */
		//doThrow(new NullPointerException()).when(mock).drawString(argThat(new IsNullString()), any(), any());
		//doThrow(new NullPointerException()).when(mock).drawString(isNull(AttributedCharacterIterator.class), any(), any());

		doReturn(true).when(mock).drawImage(any(), any(), any());

		return mock;
	}
}
