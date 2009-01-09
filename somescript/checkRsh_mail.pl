[root@console Mail]# cat checkRsh_mail.pl 
#!/usr/bin/perl -w
# checkrsh_mail.pl
# Author: cjj
# Time: 2006.9.26
# Fuction: rsh check and send mail to the mail-list

use strict;

### read the nodelist for checking
my $file1 = "/usr/local/bin/nodelist";
open( FILE , $file1 ) || ( "Could not open nodelist file!\n" );
my @allnodes = <FILE>;
close(FILE);

### delete the checklog and do rshChecking ....
system( "rm -f /home/cjj/log/checklog" );
system( "/usr/local/bin/rshCheck.pl >/home/cjj/log/checklog" );


open( FILE , "/home/cjj/log/checklog" ) || ( "Cound not open checklog file!\n" );
my @myresults = <FILE>;
close(FILE);

### read the checklog and see how many crashed nodes.
my $i = 0;
my @downnodes;
foreach ( @allnodes ) {
    chomp;
    my @compare = split( " " , $myresults[$i] );
    
    if ( ( $compare[2] eq $_ ) && ( $compare[3] eq "OK!" ) ) {
        #nothing
    }
    else {
        push @downnodes,$_;
    }
    $i++;   
}



my $time = localtime();
my $send = 1;

my $nodes = "-1";
if ( $#downnodes+1 == 0 ) {
        $nodes = "ok";
}

### check if we have already sent mail to notice the same down nodes
open( FILE , "</home/cjj/utils/Mail/log" ) || ( "Can't open the log file\n" );
my $readsomething = <FILE>;
close(FILE);
chomp( $readsomething );
my @readsomething = split( "," , $readsomething );
if ( $nodes eq $readsomething[0] ) {
        ### $send = 0 represents no sending , $send = 1 respresents sending
        $send = 0; 
}
    
  
### if there are some downnodes, we would send some email to user
if ( $#downnodes+1 > 0 ) {  
        $nodes = "\n";
        my $temp_nodes = -1;  ### to write to log , different from null
        foreach ( @downnodes ) {
                chomp;
                $nodes = $nodes . $_ . "\n";
                $temp_nodes = $temp_nodes . $_;
        }
        if ( $temp_nodes eq $readsomething[0] ) {
                ### $send = 0 represents no sending , $send = 1 respresents sending
                $send = 0; 
        }
        else {
                open( FILE , ">/home/cjj/utils/Mail/log" ) || ( "Can't open the log file\n" );
                print FILE join( "," , $temp_nodes , $time );
                close(FILE);
        }

        if ( $send == 0) {
                exit;
        }


    open( FILE , "/home/cjj/utils/Mail/mail-list" ) || ( "Cound not open checklog file!\n" );
    my @r_mail = <FILE>;
    close(FILE);
    
    ### read the mail-list one by one, and send mail
    foreach ( @r_mail ) {
        chomp;
        my($s_mail) = 'yzcaijunjie@gmail.com';
        my($subject) = 'Some nodes have been crashed';

        open( MAIL , '|/usr/lib/sendmail -t' );
        select( MAIL );

print<<END_TAG;
To: $_
From: $s_mail
Subject:  $subject

Hi,this is a auto email from HPCL!
Sorry,Some nodes have been crashed.I will solveit as quickly as I can!
    
$nodes
    
cjj
$time
    
END_TAG

    }

}
else {

        if ( $send == 0 ) {
                print "all nodes ok , and I have reported \n";
                exit;
        }
        print "All nodes are well down\n";
        open( FILE , ">/home/cjj/utils/Mail/log" ) || ( "Can't open the log file\n" );
        my $witesomething = join( "," , "ok" , $time );
        print FILE $witesomething;
        close(FILE);
        open( FILE , "/home/cjj/utils/Mail/mail-list" ) || ( "Cound not open checklog file!\n" );
        my @r_mail = <FILE>;
    close(FILE);
    
    ### read the mail-list one by one, and send mail
    foreach ( @r_mail ) {
        chomp;
        my($s_mail) = 'yzcaijunjie@gmail.com';
        my($subject) = 'OK,We have fixed some problems';

        open( MAIL , '|/usr/lib/sendmail -t' );
        select( MAIL );

print<<END_TAG;
To: $_
From: $s_mail
Subject:  $subject

Hi,this is a auto email from HPCL!
All nodes have been well done.
    
cjj
$time
    
END_TAG

    }
}

