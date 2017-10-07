import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class MusicVisualizer {

    static boolean stopped = false;

    public static void main(String[] args) {
        TargetDataLine line;
        AudioFormat format = new AudioFormat(50, 10, 1, false, false);
        System.out.print("asdf");
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format); // format is an AudioFormat object
        if (!AudioSystem.isLineSupported(info)) {
          // Handle the error ...

        }
        // Obtain and open the line.
        try {
          line = (TargetDataLine) AudioSystem.getLine(info);
          line.open(format);
            // Assume that the TargetDataLine, line, has already
            // been obtained and opened.
            ByteArrayOutputStream out  = new ByteArrayOutputStream();
            int numBytesRead;
            byte[] data = new byte[line.getBufferSize() / 5];

            // Begin audio capture.
            line.start();

            // Here, stopped is a global boolean set by another thread.
            while (!stopped) {
                // Read the next chunk of data from the TargetDataLine.
                numBytesRead =  line.read(data, 0, data.length);
                // Save this chunk of data.
                out.write(data, 0, numBytesRead);
            }
        } catch (LineUnavailableException ex) {
            // Handle the error ...
            System.out.println("file didn't work");
        }
    }
}
