/** 
 * Base class (interface) to determine the music source.
 *
 * A @see MusicSource holds the metadata, of the tracks. Subclasses such as
 * @see localSongs (local MP3s) and @see spotifySongs (soon to be made) 
 * decide the order of the songs.
 *
 *
 * */

package musicLib;

import java.util.List;

public interface MusicSource {

  List<Track> getTracks(); // List of tracks

  boolean isAvailable(); // Determine if the song is aviavable, also if next

  void setEnabled(boolean enable); // Checking if the user wants mp3 or spotify

  boolean isEnabled(); // Whether this source is currently turned on
}
