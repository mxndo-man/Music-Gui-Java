package musicLib;

/**
 * Base class for any playable song in the music library.
 *
 * 
 * A @see Track holds the metadata that every song has, no matter where
 * it comes from: an ID, a song name and an author. Subclasses such as
 * 
 * @see localFile}(local MP3s) and @see spotifyTrack} decide how the song
 *      actually plays by
 *      implementing @see #play(), @see #pause() and @see #stop().
 *
 *      The class also counts how many @see Track, instances have been
 *      created, using a static counter shared by all subclasses
 *      (see @see #getCounter()).
 *
 * @see Library
 */
public abstract class Track {
  private final String songId;
  private final String songName;
  private final String author;
  private static int counter = 0;

  // Construcutor every class should listen to. (Of course ones that use it)
  protected Track(String songId, String songName, String author) {
    this.songId = songId;
    this.songName = songName;
    this.author = author;
    counter++;
  }

  // Abstract: Play the current song, either on double click what is loaded.
  public abstract void play();

  // Abstract: Puases the current song.
  public abstract void pause();

  // Stop the songs, should be used during next and also should clear memory.
  public abstract void stop();

  public String getId() {
    return this.songId;
  }

  public String getName() {
    return this.songName;
  }

  public String getAuthor() {
    return this.author;
  }

  public static int getCounter() {
    return counter;
  }

  // Inorder to get the name and author of the file.
  @Override
  public String toString() {
    return this.songName + " - " + this.author;
  }
}
