#!/bin/bash
DIALOG=${DIALOG=dialog}
. /team/se/workspaces/setjava4ant.sh
while :
do
      $DIALOG --title " Ant Script for GLR" --clear \
        --yesno "Willst Du eine GLR-Jarpackage mit Ant erstellen?" 10 30

case $? in
     
  0)
    ant -f build_projekt.xml ;;
  1)
    break ;;
  255)
    echo "ESC pressed.";;
esac


	
done
