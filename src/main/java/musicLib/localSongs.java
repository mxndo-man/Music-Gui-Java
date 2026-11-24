package musicLib;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class localSongs implements MusicSource {
  private final List<Track> files = new ArrayList<>();
  private boolean enable = true;

  public void addFile(File mp3) {
    files.add(new localFile(mp3));
  } // Adding a file `mp3` to the list

  public int loadFolder(File dir) {
    files.clear(); // start a fresh playlist for the new folder
    // filters to only get the files that end with ".mp3"
    File[] mp3s = dir.listFiles(f -> f.isFile() && f.getName().toLowerCase().endsWith(".mp3"));
    if (mp3s == null) {
      return 0;
    }

    Arrays.sort(mp3s); // alphabetical order by file name
    for (File i : mp3s) {
      addFile(i);
    }
    return files.size();
  }

  // Should only be a read only method.
  @Override
  public List<Track> getTracks() {
    return Collections.unmodifiableList(this.files);
  }

  @Override
  public boolean isAvailable() { // With an mp3 player the music will always be avaible.
    return true;
  }

  @Override
  public void setEnabled(boolean enable) {
    this.enable = enable;
  }

  @Override
  public boolean isEnabled() {
    return this.enable;
  }

}
