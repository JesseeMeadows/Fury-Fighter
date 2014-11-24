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

		doThrow(new NullPointerException()).when(mock).drawString(argThat(new IsNullString()), any(), any());
		doThrow(new NullPointerException()).when(mock).drawString(argThat(new IsNullAttributedCharacterIterator()), any(), any());
		when(mock.drawImage(any(), any(), any())).thenReturn(true);

		return mock;
	}
}
