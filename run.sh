#!/bin/bash
#remove old
rm /tmp/.X99-lock #needed when docker container is restarted
Xvfb :99 -screen 0 640x480x8 -nolisten tcp &
mvn javafx:run