#!/bin/bash
./shell3.sh
case $2 in
     +) expr $1 + $3
;;
     -) expr $1 - $3
;;
     \*) expr $1 \* $3
;;
     ) expr $1 / $3
;;
esac
