/*
 * Based on http://www.oracle.com/technetwork/java/index-139508.html
 */

import javax.sound.sampled.*;
import java.io.File;


public class MySound implements LineListener {
    
    Object currentSound;
    String name;
    private boolean loop;

    public MySound(String fname){
        name=fname;
        loadSound(fname);
		loop=false;
    }
    public boolean getLooping(){
        return loop;
    }
    public boolean loadSound(String fname){
            File filein = new File(fname);

            try {
                currentSound = AudioSystem.getAudioInputStream(filein);
            }
            catch(Exception e1) {
                System.out.println("Could not getAudioInputStream");
                return false;
            }


           try {
                AudioInputStream stream = (AudioInputStream) currentSound;
                AudioFormat format = stream.getFormat();
                /**
                 * we can't yet open the device for ALAW/ULAW playback,
                 * convert ALAW/ULAW to PCM
                 */
                if ((format.getEncoding() == AudioFormat.Encoding.ULAW) ||
                    (format.getEncoding() == AudioFormat.Encoding.ALAW))
                {
                    AudioFormat tmp = new AudioFormat(
                                              AudioFormat.Encoding.PCM_SIGNED,
                                              format.getSampleRate(),
                                              format.getSampleSizeInBits() * 2,
                                              format.getChannels(),
                                              format.getFrameSize() * 2,
                                              format.getFrameRate(),
                                              true);
                    stream = AudioSystem.getAudioInputStream(tmp, stream);
                    format = tmp;
                }
                DataLine.Info info = new DataLine.Info(
                                          Clip.class,
                                          stream.getFormat(),
                                          ((int) stream.getFrameLength() *
                                              format.getFrameSize()));

                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(stream);
                currentSound = clip;
            }
            catch (Exception ex) {
                ex.printStackTrace();
                currentSound = null;
                return false;
            }

        return true;

    }

    public void update(LineEvent lineEvent){
        if(lineEvent.getType() == LineEvent.Type.STOP){
            Clip clip = (Clip) currentSound;
            clip.stop();
            lineEvent.getLine().close();
        }
    }

    public void playSound(){
        if (currentSound!=null){
            Clip clip = (Clip) currentSound;
            if(loop==false)
            {
                clip.stop();
                clip.setFramePosition(0);
                clip.start();
            }
            else
                clip.loop(clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stopSound(){
        if (currentSound!=null){
            Clip clip = (Clip) currentSound;
            clip.stop();
            currentSound=null;
        }
    }

    public void pauseSound(){
        if (currentSound!=null){
            Clip clip = (Clip) currentSound;
            clip.stop();
        }
    }

    public void setVolume(float percent){
        if (currentSound!=null){
            if(percent<0)percent=0;
            if(percent>100)percent=100;
            percent/=100;
            try {
                Clip clip = (Clip) currentSound;
                FloatControl gainControl =
                  (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float)
                  (Math.log(percent==0.0?0.0001:percent)/Math.log(10.0)*20.0);
                gainControl.setValue(dB);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setPan(float percent){
        if (currentSound!=null){
            if(percent<-100)percent=-100;
            if(percent>100)percent=100;
            try {
                Clip clip = (Clip) currentSound;
                FloatControl panControl =
                    (FloatControl) clip.getControl(FloatControl.Type.PAN);
                panControl.setValue(percent/100);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public void setLooping(boolean b){
        loop=b;
    }

    public boolean isPlaying(){
        if(currentSound!=null){
            Clip clip = (Clip) currentSound;
            return clip.isRunning();
        }
        else
            return false;
    }
}
