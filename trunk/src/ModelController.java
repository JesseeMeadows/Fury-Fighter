

public class ModelController{
    private ViewController viewController;
    private Model mainModel;

    ModelController(){
	this.mainModel = null;
    }

    public void update(float dt){
        if(this.mainModel != null)
	    this.mainModel.update(dt);
	//System.out.println(1000/dt); // FPS
    }
    public void setMainModel(Model m){
	this.mainModel = m;
    }
    public Model getMainModel(){
	return this.mainModel;
    }
    public void setViewController(ViewController v){
	this.viewController = v;
    }
    public ViewController getViewController(){
	return this.viewController;
    }
}
