package app;

import javafx.application.Platform;
import javax.swing.SwingUtilities;
import musicLib.Library;
import musicLib.localSongs;
import musicPlayer.Player;

/**
 * Starts the app and builds the window.
 *
 * Main only builds the buttons and passes clicks to @see Player.
 * To see how the display is being shown take a look at @see PlayerWindow.
 * It never plays songs itself, the Player decides what plays.
 */
public class Main {
  public static void main(String[] args) {
    // JavaFX has to be started before any MediaPlayer is made,
    // otherwise localFile.play() crashes with "Toolkit not initialized".
    Platform.startup(() -> {
    });
    // Keep JavaFX alive even though we never open a JavaFX window.
    Platform.setImplicitExit(false);

    Library musLib = new Library();
    localSongs local = new localSongs();
    Player player = new Player(musLib);

    // Swing windows should be built on the Swing thread.
    musLib.addSource(local);
    SwingUtilities.invokeLater(() -> new PlayerWindow(player, local).show());
  }
}
