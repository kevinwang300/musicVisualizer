//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import javax.sound.sampled.AudioFormat;
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.DataLine;
//import javax.sound.sampled.LineUnavailableException;
//import javax.sound.sampled.Mixer;
//import javax.sound.sampled.SourceDataLine;
//import javax.sound.sampled.TargetDataLine;
//
//
//
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.Clip;

package MusVisPack;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicVisualizer implements LineListener {
//
//    static boolean stopped = false;
//
//    public static void main(String[] args) {
//        TargetDataLine line;
//        AudioFormat format = new AudioFormat(50, 10, 1, false, false);
//        System.out.print("asdf");
//        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format); // format is an AudioFormat object
//        if (!AudioSystem.isLineSupported(info)) {
//          // Handle the error ...
//            System.out.println("unsupported type!");
//        }
//        // Obtain and open the line.
//        try {
//          line = (TargetDataLine) AudioSystem.getLine(info);
//          line.open(format);
//            // Assume that the TargetDataLine, line, has already
//            // been obtained and opened.
//            ByteArrayOutputStream out  = new ByteArrayOutputStream();
//            int numBytesRead;
//            byte[] data = new byte[line.getBufferSize() / 5];
//
//            // Begin audio capture.
//            line.start();
//
//            // Here, stopped is a global boolean set by another thread.
//            while (!stopped) {
//                // Read the next chunk of data from the TargetDataLine.
//                numBytesRead =  line.read(data, 0, data.length);
//                // Save this chunk of data.
//                out.write(data, 0, numBytesRead);
//            }
//        } catch (LineUnavailableException ex) {
//            // Handle the error ...
//            System.out.println("file didn't work");
//        }
//    }

//    public Clip loadClip(String filename) {
//        Clip in = null;
//
//        try {
//            System.out.println("trying");
//            AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(filename));
//            System.out.println("success!");
//            in = AudioSystem.getClip();
//            in.open(audioIn);
//        } catch (Exception e) {
//            System.out.println("Your audio file doesn't exist!");
//            //e.printStackTrace();
//        }
//
//        return in;
//    }
//
//    public void playClip(Clip clip) {
//        if (clip.isRunning()) {
//            clip.stop();
//        }
//        clip.setFramePosition(0);
//        clip.start();
//    }
//
//
//    public static void main(String[] args) {
//        MusicVisualizer mv = new MusicVisualizer();
//        Clip sound = mv.loadClip("/Kurzweil-K2000-Grand-Strings-C3.wav");
//        mv.playClip(sound);
//    }
    /**
     * this flag indicates whether the playback completes or not.
     */
    boolean playCompleted;

    /**
     * Play a given audio file.
     * @param audioFilePath Path of the audio file.
     */
    void play(String audioFilePath) {
        File audioFile = new File(audioFilePath);

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Clip audioClip = (Clip) AudioSystem.getLine(info);

            audioClip.addLineListener(this);

            audioClip.open(audioStream);

            audioClip.start();

            while (!playCompleted) {
                // wait for the playback completes
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            audioClip.close();

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }

    }

    /**
     * Listens to the START and STOP events of the audio line.
     */
    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();

        if (type == LineEvent.Type.START) {
            System.out.println("Playback started.");

        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            System.out.println("Playback completed.");
        }

    }

    public static void main(String[] args) {
        String audioFilePath = "/Users/ryanleung/calHacks2017/MusicVisualizer/Kurzweil-K2000-Grand-Strings-C3.wav";
        MusicVisualizer player = new MusicVisualizer();
        player.play(audioFilePath);
    }
}