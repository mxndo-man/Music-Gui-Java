#!/usr/bin/env bash
#
# Description: Starts the apllication gui app and sets up the dependiecies.
# Prerequistes: Git Bash (For windows) or a Unix terminal.
#   Latest version of Maven/mvn,
#   Java atleast installed.
#
#
# How to run it: Be in a unix terminal.
#   cd /script/
#   chmod +x start.sh
#
#   ./script.sh up (This will start the gui)

set -euo pipefail

SCRIPT_PATH="$(cd "$(dirname "${BASH_SOURCE}")" && pwd)"
cd "$SCRIPT_PATH/.."

if ! command -v mvn &> /dev/null; then
    echo "mvn is not installed. Please install 'mxn'."
    exit 1
fi

if ! command -v java &> /dev/null; then
    echo "Java is not installed. Please install 'Java', check repo to see version ." 
    exit 1
fi

usage() {
    echo "Usage: $0 up"
    exit 1
}

if [[ $# -lt 1 ]]; then
    usage
fi

case "$1" in
    up)
        echo "Starting up Music Gui Java"
        mvn --version 
        java --version
        mvn javafx:run
        ;;
    *)
        usage
        ;;
esac

