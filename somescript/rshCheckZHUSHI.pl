#!/usr/bin/perl 
# rshCheckAll

#节点表
$fn="/usr/local/bin/nodelist"; 
#创建nodelist的文件句柄,如果创建失败则输出信息"Could not open file"
open(FNODES,$fn)||("Could not open file");

#读取文件中所有的内容到列表@all中
my @all=<FNODES>; 
close(FNODES);

#执行命令hostname,结果输出到$console中
$console=`/bin/hostname`;
#删除末尾的\n
chomp $console;

$err_flag=0;
foreach(@all) {
	      #删除其中一个节点的末尾的\n,也可以写成chomp $_;
        chomp;
        #将节点的信息按照逗号分割,并把结果存到列表nodes中
        @nodes=split(/,/);
        #循环扫描每个节点经过分割的值
        for($i=0;$i<@nodes;$i++){
                if($i==100){
                        sleep(10);
                }
                if($i==200){
                        sleep(10);
                }
                #print "command is $command\n";
                #以下的代码主要是进行当前主机与节点主机的双向测试连通情况
                #qx是指执行里面内容
                $result = qx(/usr/bin/rsh $nodes[$i] echo ABCD);  
                chomp $result;
                if($result eq "ABCD"){
                        #print("/usr/bin/rsh $nodes[$i] \"/usr/bin/rsh $console echo ABCD\"\n");
                        $result = qx(/usr/bin/rsh $nodes[$i] \"/usr/bin/rsh $console echo ABCD\");
                        chomp $result;
                        if($result eq "ABCD"){
                                print("successrsh check $nodes[$i] OK!\n");
                        }else{
                                $err_flag=1;
                                # changed by cjj 2006.9.26   original: "\n" 
                                print("error : could not rsh from $nodes[$i] to $console \n");
                        }
                }else{
                        # changed by cjj 2006.9.26  original: "\n"
                        print("error : could not rsh from $console to $nodes[$i] \n");
                        $err_flag=1;
                }
        #       print "rsh check $nodes[$i] OK!\n";
        }
}
if ($err_flag==1) {
        print("rsh check failed!\n");
        exit(1);
}
print "rsh check over: all nodes in /usr/local/bin/nodelist success!\n";         

