public class ModelController {
	private ViewController viewController;
	private Model mainModel;

/**
 * This class is a lot like the ViewController class, in being that it's basically a wrapper.
 * It's only function is to hold the current model that's in use and call that model's
 * functions/methods through polymorphism
 *
 * Functions:
 * 1) Hold active model(typically levelModel, but the splash screen and title models are other options)
 * 2) Give active model ability to set a different active model and viewController(associated with that given model)
 */
	ModelController(Model m) {
		mainModel = m;

		if (mainModel != null)
			mainModel.setModelController(this);
	}

	public void update(float dt) {
		if (mainModel != null)
			mainModel.update(dt);
		// System.out.println(1000/dt); // FPS
	}

	public void setMainModel(Model m) {
		mainModel = m;
	}

	public Model getMainModel() {
		return mainModel;
	}

	public void setViewController(ViewController v) {
		viewController = v;
	}

	public ViewController getViewController() {
		return viewController;
	}
}
