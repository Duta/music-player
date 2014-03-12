package musicplayer.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBuilder;
import javafx.stage.FileChooser;
import javafx.stage.FileChooserBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;
import musicplayer.audio.Audio;
import musicplayer.audio.AudioFactory;

import java.io.File;

public class MusicPlayer extends Application {
    private String name;
    private Audio audio;
    private Text nowPlayingText;
    private Slider playbackSlider;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        // Set the title
        stage.setTitle("Music Player");

        // Create now playing text
        nowPlayingText = TextBuilder.create()
                .textAlignment(TextAlignment.CENTER)
                .build();

        // Create the playback slider
        playbackSlider = SliderBuilder.create()
                .min(0)
                .max(0)
                .majorTickUnit(60)
                .minorTickCount(12)
                .blockIncrement(5)
                .snapToTicks(true)
                .build();

        // Create the buttons
        Button playButton = ButtonBuilder.create()
                .text("Play")
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(audio != null) {
                            audio.play();
                        }
                    }
                })
                .build();
        Button pauseButton = ButtonBuilder.create()
                .text("Pause")
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(audio != null) {
                            audio.pause();
                        }
                    }
                })
                .build();

        // Create the menu items
        MenuItem openItem = MenuItemBuilder.create()
                .text("Open")
                .accelerator(new KeyCodeCombination(
                        KeyCode.O, KeyCombination.CONTROL_DOWN))
                .onAction(new EventHandler<ActionEvent>() {
                    private final FileChooser chooser = FileChooserBuilder.create()
                            .title("Open Music File")
                            .extensionFilters(
                                    new FileChooser.ExtensionFilter(
                                            "Music files (*.wav, *.aiff, *.au)",
                                            "*.wav", "*.wave",
                                            "*.aiff", "*.aif",
                                            "*.au", "*.snd"))
                            .initialDirectory(
                                    new File(System.getProperty("user.home")))
                            .build();

                    @Override
                    public void handle(ActionEvent actionEvent) {
                        File file = chooser.showOpenDialog(stage);
                        if(file != null) {
                            name = file.getName();
                            audio = AudioFactory.getInstance().getAudio(file);
                            playbackSlider.setValue(0);
                            if(audio != null) {
                                playbackSlider.setMax(audio.getTotalSecs());
                            } else {
                                playbackSlider.setMax(0);
                            }
                        }
                    }
                })
                .build();

        // Create the menus
        Menu fileMenu = MenuBuilder.create().text("File").items(openItem).build();

        // Set up the scene
        MenuBar menuBar = MenuBarBuilder.create().menus(fileMenu).build();
        VBox buttons = VBoxBuilder.create()
                .alignment(Pos.CENTER)
                .children(nowPlayingText, playbackSlider, playButton, pauseButton)
                .spacing(10)
                .build();
        StackPane main = StackPaneBuilder.create()
                .padding(new Insets(20))
                .children(menuBar, buttons)
                .build();
        VBox root = VBoxBuilder.create().children(menuBar, main).build();
        stage.setScene(new Scene(root));

        // Show the window
        stage.show();

        // Get the periodic update going
        Timeline timeline = TimelineBuilder.create()
                .cycleCount(Timeline.INDEFINITE)
                .keyFrames(
                        new KeyFrame(
                                Duration.seconds(1),
                                new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent actionEvent) {
                                        update();
                                    }
                                }))
                .build();
        timeline.playFromStart();
    }

    private void update() {
        if(audio != null) {
            nowPlayingText.setText(String.format(
                    "Playing '%s' - %s / %s",
                    name,
                    formatSecs(audio.getCurrentSecs()),
                    formatSecs(audio.getTotalSecs())));
            playbackSlider.setValue(audio.getCurrentSecs());
        } else {
            nowPlayingText.setText("No music loaded");
        }
    }

    private String formatSecs(long secs) {
        return String.format("%d:%02d", secs / 60, secs % 60);
    }
}
