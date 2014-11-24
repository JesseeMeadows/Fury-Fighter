import java.util.HashMap;

public abstract class Model{
	protected ModelController modelController;

	public void setModelController(ModelController m)
	{
		modelController = m;
	}

    public abstract int update(float dt, int scrollDelta);
    public abstract HashMap<String,Model> getVisibleModels();
}
