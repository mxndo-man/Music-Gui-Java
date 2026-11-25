package musicLib;

import java.util.ArrayList;
import java.util.List;

/**
 * A class where users can see their library for the music gui.
 * 
 * The library holds every @see MusicSource (local MP3s, Spotify) and
 * combines their tracks into a single list.
 */
public class Library {
  private final List<MusicSource> sources = new ArrayList<>();

  public void addSource(MusicSource s) {
    if (!sources.contains(s)) { // stop duplicates from being added
      sources.add(s);
    }
  }

  public void removeSource(MusicSource s) {
    sources.remove(s); // does nothing if s isn't in the list
  }

  public List<Track> combineTracks() {
    List<Track> result = new ArrayList<>();
    for (MusicSource i : sources) {
      if (i.isAvailable() && i.isEnabled()) {
        result.addAll(i.getTracks());
      }
    }

    return result;
  }

  public boolean isEmpty() {
    return combineTracks().isEmpty(); // Will be used for the skip feature.
  }

}
