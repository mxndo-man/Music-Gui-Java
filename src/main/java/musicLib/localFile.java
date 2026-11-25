package musicLib;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import com.mpatric.mp3agic.Mp3File;
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
    super(file.getAbsolutePath(), stripExtension(file.getName()), getArtist(file));
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

private static String getArtist(File file) {
    try {
        Mp3File mp3 = new Mp3File(file.getAbsolutePath());

        if (mp3.hasId3v2Tag()) {
            String artist = mp3.getId3v2Tag().getArtist();

            if (artist != null && !artist.isBlank()) {
                return artist;
            }
        }

        if (mp3.hasId3v1Tag()) {
            String artist = mp3.getId3v1Tag().getArtist();

            if (artist != null && !artist.isBlank()) {
                return artist;
            }
        }

    } catch (Exception e) {
        System.err.println("Unable to read artist metadata: "
                + e.getMessage());
    }

    return "Unknown Artist";
}



}
