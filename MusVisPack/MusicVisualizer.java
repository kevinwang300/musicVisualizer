package MusVisPack;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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
    /**
     * this flag indicates whether the playback completes or not.
     */
    boolean playCompleted;
    Turtle visualizer = new Turtle(0.5, 0.5, 0);
    /**
     * Play a given audio file.
     * @param audioFilePath Path of the audio file.
     */
    void play(String audioFilePath) {
        File audioFile = new File(audioFilePath);
        int counter = 1;
        System.out.println("Enter the beats per minute: ");
        Scanner scanner = new Scanner(System.in);
        int bpm = scanner.nextInt();


        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Clip audioClip = (Clip) AudioSystem.getLine(info);

            audioClip.addLineListener(this);

            audioClip.open(audioStream);
            StdDraw.setCanvasSize(720, 720);
            StdDraw.clear(StdDraw.BLACK);
            audioClip.start();
            while (!playCompleted) {
                // wait for the playback completes
                try {
                    Thread.sleep(1000 / bpm);
                    int loopLength = 50;
                    int clearCount = 200;
                    visualizer.goForward((counter % loopLength) * 0.01);
                    visualizer.turnLeft(90.25);
                    counter++;
                    if ((counter % clearCount) == 0) {
                        visualizer.x = 0.75;
                        visualizer.y = 0.75;
                        visualizer.angle = 5 * (counter / loopLength);
                        visualizer.setPenColor(Color.getHSBColor((float) Math.random(), .8f, .8f));
                    }
                    if ((counter % clearCount) == loopLength) {
                        visualizer.x = 0.25;
                        visualizer.y = 0.75;
                        visualizer.angle = 5 * (counter / loopLength);
                        visualizer.setPenColor(Color.getHSBColor((float) Math.random(), .8f, .8f));
                    }
                    if ((counter % clearCount) == 2 * loopLength) {
                        visualizer.x = 0.25;
                        visualizer.y = 0.25;
                        visualizer.angle = 5 * (counter / loopLength);
                        visualizer.setPenColor(Color.getHSBColor((float) Math.random(), .8f, .8f));
                    }
                    if ((counter % clearCount) == 3 * loopLength) {
                        visualizer.x = 0.75;
                        visualizer.y = 0.25;
                        visualizer.angle = 5 * (counter / loopLength);
                        visualizer.setPenColor(Color.getHSBColor((float) Math.random(), .8f, .8f));
                    }
                    if ((counter % clearCount) == 0) {
                        StdDraw.clear(StdDraw.BLACK);
                    }
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
        System.out.println("Enter an audio file path:");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        //System.out.println("Your username is " + username);
        String audioFilePath = "/Users/dangdang98/calHacks2017/musicVisualizer/MusVisPack/1-06 Redbone.wav";
        MusicVisualizer player = new MusicVisualizer();
        player.play(username);
    }
}