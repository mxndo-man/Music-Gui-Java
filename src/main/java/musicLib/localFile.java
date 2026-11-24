package musicLib;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

/**
 * This file should only handle local files, mp3 files.
 *
 * For more ref take a look at @see Track, its abtract class.
 *
 */

public class localFile extends Track {
  private MediaPlayer musicPlayer;
  private final File file;

  public localFile(File file) {
    super(file.getAbsolutePath(), stripExtension(file.getName()), "Unknown Artist");
    this.file = file;
  }
  // "Song.MP3" -> "Song", "my.song.mp3" -> "my.song" (only removes the last
  // extension)

  // getting the index of where .mp3 is located.
  private static String stripExtension(String name) {
    int dot = name.lastIndexOf('.');

    return dot > 0 ? name.substring(0, dot) : name;
  }

  @Override
  public void pause() {
    if (musicPlayer != null) {
      musicPlayer.pause();
    }
  }

  @Override
  public void play() {
    if (musicPlayer == null) {
      musicPlayer = new MediaPlayer(new Media(file.toURI().toString()));
    }
    musicPlayer.play();

  }

  @Override
  public void stop() {
    if (musicPlayer != null) {
      musicPlayer.stop();
      musicPlayer.dispose();
      musicPlayer = null;
    }
  }

}
