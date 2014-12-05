import static org.junit.Assert.*;
import org.junit.*;

import java.util.HashMap;

public class TestEnemyView{
	private FlyerModel enemyModel;
	private EnemyView enemyView;
	
	@Before
	public void initialize()
	{
		enemyModel = new FlyerModel(0,0);
		enemyView = new EnemyView(enemyModel);
	}
	
	@Test
	public void testRenderEnemyView()
	{
		enemyView.render(Graphics2DMock.getMockObject(),0,0);
	}
	
	@Test
	public void testGetVisibleViewsEnemyView()
	{
		assertTrue(enemyView.getVisibleViews() instanceof HashMap);
		assertEquals(0, enemyView.getVisibleViews().size());
	}
	
	@Test
	public void testGetEnemyModel()
	{
		assertTrue(enemyView.getEnemyModel() instanceof EnemyModel);
		assertEquals(enemyModel, enemyView.getEnemyModel());
	}
	
}