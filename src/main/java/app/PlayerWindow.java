package app;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import musicLib.Track;
import musicLib.localSongs;
import musicPlayer.Player;

/**
 * A file to start the playerWindow.
 *
 * To view how it works a Java Swing tut should explain what is happening.
 *
 * This file should only be stored in /app/ and used in @see Main.
 * If any other changes were to be made just follow apache standard and how main
 * is written.
 */

public class PlayerWindow {
  private final Player player;
  private final localSongs local;

  private final DefaultListModel<Track> songRows = new DefaultListModel<>();
  private final JList<Track> songList = new JList<>(songRows);
  private final JLabel nowPlaying = new JLabel("No song");
  private final JButton playButton = new JButton("Play");
  private final JFrame frame = new JFrame("Chunky Pod");

  public PlayerWindow(Player player, localSongs local) {
    this.player = player;
    this.local = local;
  }

  public void show() {
    // In order to pick the music folder.
    JButton openButton = new JButton("Open Folder");
    openButton.addActionListener(e -> openFolder());

    // Playback controls, like pause,play and next.
    JButton prevButton = new JButton("Prev");
    JButton nextButton = new JButton("Next");

    prevButton.addActionListener(e -> {
      player.previous();
      refresh();
    });

    playButton.addActionListener(e -> {
      player.togglePlay();
      refresh();
    });

    nextButton.addActionListener(e -> {
      player.next();
      refresh();
    });

    JPanel controls = new JPanel();
    controls.add(prevButton);
    controls.add(playButton);
    controls.add(nextButton);

    JPanel bottom = new JPanel(new BorderLayout());
    bottom.add(nowPlaying, BorderLayout.NORTH);
    bottom.add(controls, BorderLayout.SOUTH);

    // Double click a song to play it.
    songList.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          player.playAt(songList.getSelectedIndex());
          refresh();
        }
      }
    });

    frame.add(openButton, BorderLayout.NORTH);
    frame.add(new JScrollPane(songList), BorderLayout.CENTER);
    frame.add(bottom, BorderLayout.SOUTH);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // X closes the app, no need to ctrl + c or stop in script.
    frame.setSize(320, 480);
    frame.setLocationRelativeTo(null); // center on screen
    frame.setVisible(true); // Make sure everything is avaiable for the user to see.
  }

  // Simply shows the directory/file explorer so the user can pick out which
  // folder.
  private void openFolder() {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    if (chooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
      return; // user pressed cancel
    }

    File folder = chooser.getSelectedFile();
    int count = local.loadFolder(folder);
    if (count == 0) {
      JOptionPane.showMessageDialog(frame, "No MP3 files found in " + folder.getName());
    }

    player.onSourcesChanged();

    // Rebuild the rows from the player's queue.
    songRows.clear();
    for (Track t : player.getQueue()) {
      songRows.addElement(t);
    }
    refresh();
  }

  private void refresh() {
    nowPlaying.setText(player.current().map(Track::toString).orElse("No song"));
    playButton.setText(player.isPlaying() ? "Pause" : "Play");

    // Highlight the current song in the list.
    int i = player.current().map(t -> player.getQueue().indexOf(t)).orElse(-1);
    if (i >= 0) {
      songList.setSelectedIndex(i);
      songList.ensureIndexIsVisible(i);
    } else {
      songList.clearSelection();
    }
  }

}
