#abc

#some problem
#./my.sh  1  2


MC="~/.mc/tmp/mc-$$"
/usr/bin/mc -P > "$MC"
cd "`cat $MC`"
/bin/rm "$MC"
unset MC; 
