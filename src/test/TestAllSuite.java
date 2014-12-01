import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=org.junit.runners.Suite.class)
@SuiteClasses(value={

		TestMillisecTimer.class,
		TestUtils.class,
		TestFlyerModel.class,
		TestEnemyBullet.class,
		TestInputResponder.class,
		TestScoreTable.class,
		TestInputHandler.class,
		TestSplashModel.class,
		TestTitleModel.class,
		TestGameOverModel.class,
		TestRingBullet.class,
		TestSoundManager.class,
		TestPlayerModel.class,
		TestWeaponPickup.class,
		TestModelController.class,
		TestPickup.class,
		TestBullet.class,
		TestEnemyModel.class,
		TestTitleView.class,
		TestLevelModel.class,

})
public class TestAllSuite {}
