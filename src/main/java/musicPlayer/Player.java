package musicPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import musicLib.Library;
import musicLib.Track;

/**
 * Controls what is playing.
 *
 * The player keeps a copy of the library's tracks (the queue) and the
 * position of the song that is playing (index). The current song is always
 * looked up from those two, so they can never disagree (see
 * {@see #current()}).
 */
public class Player {

  private final Library musLib;
  private List<Track> queue = new ArrayList<>();
  private int index = -1; // -1 means "no song selected"
  private boolean isPlaying = false;

  public Player(Library musLib) {
    this.musLib = musLib;
  }

  // The song at the current position, or Optional.empty() if there isn't one.
  public Optional<Track> current() {
    if (index >= 0 && index < queue.size()) {
      return Optional.of(queue.get(index));
    }
    return Optional.empty();
  }

  /**
   * Call this after the library changes (folder loaded, source turned on/off).
   * Rebuilds the queue and keeps playing the same song if it's still there.
   * This would also be used when we are using the Spotify API call.
   */
  public void onSourcesChanged() {
    // We always need a ref to where we are for the MP3 player.
    Optional<Track> cur = current();

    queue = musLib.combineTracks();

    if (cur.isPresent() && queue.contains(cur.get())) {
      // Same song still in the list, it may just have moved position.
      index = queue.indexOf(cur.get());
      return;
    }

    // The old song is gone (new folder was loaded), so let us stop it.
    if (cur.isPresent()) {
      cur.get().stop();
      isPlaying = false;
    }
    // Select the first song (or nothing if empty), but don't auto-play.
    // if (queue.isEmpty()) {
    // index = -1;
    // } else {
    // index = 0;
    // }
    index = queue.isEmpty() ? -1 : 0;
  }

  // Play the song at position i in the queue.
  public void playAt(int i) {
    if (i < 0 || i >= queue.size()) {
      return; // ignore bad positions instead of crashing
    }
    current().ifPresent(Track::stop); // only one song plays at a time
    index = i;
    queue.get(i).play();
    isPlaying = true;
  }

  // The play/pause button.
  public void togglePlay() {
    Optional<Track> cur = current();
    if (cur.isEmpty()) {
      return; // nothing loaded
    }
    Track t = cur.get(); // safe, we just checked
    if (isPlaying) {
      t.pause();
      isPlaying = false;
    } else {
      t.play(); // resumes where it was paused
      isPlaying = true;
    }
  }

  // Next song, wraps from the last song back to the first.
  public void next() {
    if (!queue.isEmpty()) {
      playAt((index + 1) % queue.size());
    }
  }

  // Previous song, wraps from the first song to the last.
  public void previous() {
    if (!queue.isEmpty()) {
      playAt((index - 1 + queue.size()) % queue.size());
    }
  }

  public List<Track> getQueue() {
    return queue;
  }

  public boolean isPlaying() {
    return isPlaying;
  }
}
