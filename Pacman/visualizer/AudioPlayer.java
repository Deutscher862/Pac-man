package Pacman.visualizer;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class AudioPlayer {
    private final MediaPlayer chompPlayer;
    private final MediaPlayer deadGhostPlayer;

    public AudioPlayer(){
        this.chompPlayer = new MediaPlayer(new Media(new File("src/resources/audio/pacman_chomp.wav").toURI().toString()));
        this.deadGhostPlayer = new MediaPlayer(new Media(new File("src/resources/audio/pacman_eatghost.wav").toURI().toString()));
    }

    public void playSound(String path){
        Media sound = new Media(new File(path).toURI().toString());
        MediaPlayer player = new MediaPlayer(sound);
        player.play();
    }

    public void playChompSound(){
        chompPlayer.stop();
        chompPlayer.play();
    }

    public void playDeadGhostSound(){
        deadGhostPlayer.stop();
        deadGhostPlayer.play();
    }

}