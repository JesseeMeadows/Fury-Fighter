
public class main {

	public static void main(String[] args) {
		System.out.println("Fury Fighter");

		SoundManager sm = SoundManager.get();
		sm.addSound("intro", "assets/snd/introjingle.wav");
		sm.addSound("bullet", "assets/snd/bullet.wav");
		sm.addSound("missle", "assets/snd/missle.wav");
		sm.addSound("laser", "assets/snd/laser.wav");
		sm.addSound("ring", "assets/snd/ring.wav");
		sm.addSound("interface", "assets/snd/interface.wav");
		sm.addSound("hit", "assets/snd/hit.wav");
		sm.addSound("death", "assets/snd/death.wav");
		sm.addSound("kill", "assets/snd/kill.wav");
		sm.addSound("music", "assets/snd/music.wav");
		sm.addSound("pickup", "assets/snd/pickup.wav");
		sm.addSound("fragment", "assets/snd/fragment.wav");
		sm.setLooping("music", true);

		ModelController modelController = new ModelController();
		ViewController viewController = new ViewController();

		viewController.setModelController(modelController);
		modelController.setViewController(viewController);

		modelController.setMainModel(new LevelModel(modelController, "assets/test_level.json"));
		viewController.setMainView(new LevelView(viewController, "assets/test_level.png"));
		// modelController.setMainModel(new SplashModel(modelController)); // Controller for Opening screen
		// viewController.setMainView(new SplashView(viewController)); // View for opening screen

		MillisecTimer timer = new MillisecTimer();
		float MILLISEC_PER_FRAME = (float) 1000 / 30; // 30 FPS
		int FRAME_SKIP = 0;

		// Go to next frame if sufficient time has elapsed(~33.3333...)
		while (true) {
			float elapsed = timer.getDt();
			if (elapsed >= MILLISEC_PER_FRAME) {
				modelController.update(elapsed);
				FRAME_SKIP = 0;
				timer.reset();
				viewController.render();
			}
			else {
				FRAME_SKIP += 1;
			}

		}

	}
}
