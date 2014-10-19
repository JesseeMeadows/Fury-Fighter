

public class main{

    public static void main(String [] args){
	System.out.println("Fury Fighter");
	
	SoundManager sm = SoundManager.get();
	sm.addSound("intro","assets/snd/introjingle.wav");
	sm.addSound("bullet","assets/snd/bullet.wav");
	sm.addSound("missle","assets/snd/missle.wav");
	sm.addSound("laser","assets/snd/laser.wav");
	sm.addSound("ring","assets/snd/ring.wav");
	sm.addSound("interface","assets/snd/interface.wav");
	sm.addSound("hit","assets/snd/hit.wav");
	sm.addSound("death","assets/snd/death.wav");
	sm.addSound("kill","assets/snd/kill.wav");
	sm.addSound("music","assets/snd/music.wav");
	sm.addSound("pickup","assets/snd/pickup.wav");
	sm.addSound("fragment","assets/snd/fragment.wav");
	sm.setLooping("music",true);

	ModelController m = new ModelController();
	ViewController v = new ViewController();
	
	v.setModelController(m);
	m.setViewController(v);

	//m.setMainModel(new LevelModel(m));
	//v.setMainView(new LevelView(v));
	m.setMainModel(new SplashModel(m));
	v.setMainView(new SplashView(v));
	
	MillisecTimer t = new MillisecTimer();
	float MILLISEC_PER_FRAME = (float)1000/30; //30 FPS
	int FRAME_SKIP =0;
	
	while(true){
	    float elapsed = t.getDt();
	    if(elapsed >= MILLISEC_PER_FRAME){
		m.update(elapsed);
		FRAME_SKIP = 0;
		t.reset();
		v.render();
	    }
	    else{
		FRAME_SKIP += 1;
	    }
	    
	}
	
    }
}
