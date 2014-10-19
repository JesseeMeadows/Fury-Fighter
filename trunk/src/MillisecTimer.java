import java.util.Date;

public class MillisecTimer {
    private Date curtime,oldtime,starttime;
    
    MillisecTimer(){
        starttime=new Date();
        oldtime=new Date();
        curtime=new Date();
    }
    
    public float getDt(){
        curtime=new Date();
        float tmp=curtime.getTime()-oldtime.getTime();
        return tmp;
    }
    public void reset(){
        oldtime= new Date();
        curtime= new Date();
    }
    public float getTotalElapsed(){
        Date t=new Date();
        return t.getTime()-starttime.getTime();
    }
            
    
}
