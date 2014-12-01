import java.util.HashMap;

public class SoundManager {

    private static HashMap<String,MySound> sound_list;
    private static SoundManager instance=null;

    private SoundManager(){
        sound_list=new HashMap<String,MySound>();
    }
    public static SoundManager get(){
        if (instance==null){
            instance=new SoundManager();
        }
        return instance;
    }
    public static MySound getMySound(String name){
        MySound tmp = sound_list.get(name);
        return tmp;
    }
    public static int addSound(String name,String fname){
        sound_list.put(name,new MySound(fname));
        return 0;
    }
    public static int playSound(String name){
        MySound tmp=sound_list.get(name);
        if(tmp!=null){
            tmp.playSound();
            return 0;
        }
        else{
            return 1;
        }
    }
    public static int pauseSound(String name){
        MySound tmp=sound_list.get(name);
        if(tmp!=null){
            tmp.pauseSound();
            return 0;
        }
        else{
            return 1;
        }
    }
    public static int stopSound(String name){
        MySound tmp=sound_list.get(name);
        if(tmp!=null){
            tmp.stopSound();
            return 0;
        }
        else{
            return 1;
        }
    }
    public static int setLooping(String name,boolean b){
        MySound tmp=sound_list.get(name);
        if(tmp!=null){
            tmp.setLooping(b);
            return 0;
        }
        else{
            return 1;
        }
    }

    public static int setVolume(String name,float percent){
        MySound tmp=sound_list.get(name);
        if(tmp!=null){
            tmp.setVolume(percent);
            return 0;
        }
        else{
            return 1;
        }
    }
    public static int setPan(String name,float percent){
        MySound tmp=sound_list.get(name);
        if(tmp!=null){
            tmp.setPan(percent);
            return 0;
        }
        else{
            return 1;
        }
    }
    public static boolean isPlaying(String name){
        MySound tmp=sound_list.get(name);
        if(tmp!=null){
            return tmp.isPlaying();
        }
        return false;
    }




    

}
