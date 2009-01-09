#!/usr/bin/perl 
# rExecAll(command)

$fn="/usr/local/bin/nodelist";
my $command=$ARGV[0];
open(FNODES,$fn)||("Could not open file");
my @all=<FNODES>;
close(FNODES);
foreach(@all) {
        chomp;
        @nodes=split(/,/);
        for($i=0;$i<@nodes;$i++){
                print "$nodes[$i]:\n";
                if($i==100){
                        #sleep(60);
                }
                if($i==200){
                        #sleep(60);
                }
                print "command is $command\n";
                system("/usr/bin/rsh $nodes[$i] $command");  
        }
}
    
print "rExecAll.pl over!\n";     
