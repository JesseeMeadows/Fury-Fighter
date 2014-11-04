import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=org.junit.runners.Suite.class)
@SuiteClasses(value={
		TestMillisecTimer.class,
		TestUtils.class,
		TestFlyerModel.class,
		TestEnemyBullet.class,

})
public class TestAllSuite {}
