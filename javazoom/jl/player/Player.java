package javazoom.jl.player;

import java.io.InputStream;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.SampleBuffer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;


public class Player
{
  private int frame = 0;
  




  private Bitstream bitstream;
  



  private Decoder decoder;
  



  private AudioDevice audio;
  



  private boolean closed = false;
  



  private boolean complete = false;
  
  private int lastPosition = 0;
  


  public Player(InputStream stream)
    throws JavaLayerException
  {
    this(stream, null);
  }
  
  public Player(InputStream stream, AudioDevice device) throws JavaLayerException
  {
    bitstream = new Bitstream(stream);
    decoder = new Decoder();
    
    if (device != null)
    {
      audio = device;
    }
    else
    {
      FactoryRegistry r = FactoryRegistry.systemRegistry();
      audio = r.createAudioDevice();
    }
    audio.open(decoder);
  }
  
  public void play() throws JavaLayerException
  {
    play(Integer.MAX_VALUE);
  }

  public boolean setGain(float newGain) {
    if (audio instanceof JavaSoundAudioDevice) {
      System.out.println("InstanceOf");
      JavaSoundAudioDevice jsAudio = (JavaSoundAudioDevice) audio;
      try {
        jsAudio.write(null, 0, 0);
      } catch (JavaLayerException ex) {
        ex.printStackTrace();
      }
      return jsAudio.setLineGain(newGain);
    }
    return false;
  }





  public boolean play(int frames)
    throws JavaLayerException
  {
    boolean ret = true;
    
    while ((frames-- > 0) && (ret))
    {
      ret = decodeFrame();
    }
    
    if (!ret)
    {

      AudioDevice out = audio;
      if (out != null)
      {
        out.flush();
        synchronized (this)
        {
          complete = (!closed);
          close();
        }
      }
    }
    return ret;
  }
  




  public synchronized void close()
  {
    AudioDevice out = audio;
    if (out != null)
    {
      closed = true;
      audio = null;
      

      out.close();
      lastPosition = out.getPosition();
      try
      {
        bitstream.close();
      }
      catch (BitstreamException ex) {}
    }
  }
  








  public synchronized boolean isComplete()
  {
    return complete;
  }
  






  public int getPosition()
  {
    int position = lastPosition;
    
    AudioDevice out = audio;
    if (out != null)
    {
      position = out.getPosition();
    }
    return position;
  }
  public /* synthetic */void setVolume(double vol) {
    try {
      Mixer.Info[] infos = AudioSystem.getMixerInfo();
      for (Mixer.Info info : infos) {
        Mixer mixer = AudioSystem.getMixer(info);
        if (mixer.isLineSupported(Port.Info.SPEAKER)) {
          Port port = (Port) mixer.getLine(Port.Info.SPEAKER);
          port.open();
          if (port.isControlSupported(FloatControl.Type.VOLUME)) {
            FloatControl volume = (FloatControl) port.getControl(FloatControl.Type.VOLUME);
            volume.setValue((float) (vol / 100));
          }
          port.close();
        }
      }
    } catch (Exception e) {
    }
  }





  protected boolean decodeFrame()
    throws JavaLayerException
  {
    try
    {
      AudioDevice out = audio;
      if (out == null) {
        return false;
      }
      Header h = bitstream.readFrame();
      
      if (h == null) {
        return false;
      }
      
      SampleBuffer output = (SampleBuffer)decoder.decodeFrame(h, bitstream);
      
      synchronized (this)
      {
        out = audio;
        if (out != null)
        {
          out.write(output.getBuffer(), 0, output.getBufferLength());
        }
      }
      
      bitstream.closeFrame();
    }
    catch (RuntimeException ex)
    {
      throw new JavaLayerException("Exception decoding audio frame", ex);
    }
    
















    return true;
  }
}
