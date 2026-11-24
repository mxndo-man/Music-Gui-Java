# Spotify iPod Touch GUI

Essentially, this is an MP3 player clone built with Java Swing where people can upload MP3s locally or connect to Spotify to get a nostalgic feel of the classic app.

## Features
- **Classic MP3 Interface:** Recreates the nostalgic look and feel of the original MP3 player using Java Swing.
- **Local MP3 Playback:** Upload and play your own local `.mp3` audio files directly in the desktop app.
- **Spotify Integration:** Connect your Spotify account to stream your playlists and tracks through the retro UI.

## Prerequisites
- **Java Development Kit (JDK):** Java 11 or higher installed on your machine.
- **Spotify Developer Account:** *(Optional)* Required if configuring API credentials for Spotify playback.

## Getting Started

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/mxndo-man/Music-Gui-Java.git
   ```
2. Navigate to the project directory:
   ```bash
   cd Music-Gui-Java
   ```

### Running the App
It is recommended that you use a real IDE such as VSCode, Sublime text, etc but never github codespaces to run the app. 
Check `/scripts/start.sh` and read the documentation at the top of the file for instructions on how to build and run the app.

*(Note: If you plan on using the Spotify features, make sure to update the `.env` file with your API keys—a Spotify Premium account is required.)*

## Usage
1. Launch the desktop window.
2. Select **Local Files** to browse and upload `.mp3` tracks from your computer, or log in with **Spotify** to browse your library.
3. Use the on-screen iPod controls to play, pause, and skip tracks.

## Tech Stack
- **Language:** Java
- **GUI Framework:** Java Swing / AWT
- **APIs & Libraries:** Spotify Web API *(plus any MP3 audio libraries used, e.g., JLayer / JavaFX Media)*
