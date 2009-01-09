先建普通用户的rsh信任
rshcheck检测各节点的开与关
[root@console bin]# cat rshCheck.pl
#!/usr/bin/perl 
# rshCheckAll

# 节点表
$fn="/usr/local/bin/nodelist"; 
#创建nodelist的文件句柄,如果创建失败则输出信息"Could not open file"
open(FNODES,$fn)||("Could not open file");

#读取文件中所有的内容到列表@all中
my @all=<FNODES>; #my 为私有变量声明 <>表表示输入/输出的文件句柄，如果你里面什么都不放的话也是可以的 
close(FNODES);

#得到hostname即console
$console=`/bin/hostname`
#去掉字符串后的\n
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
[root@console bin]# ./rshCheck.pl
c0101: Connection refused
error : could not rsh from console to c0101 
successrsh check c0102 OK!

successrsh check c0109 OK!

successrsh check c0112 OK!

successrsh check c0120 OK!

successrsh check c0124 OK!

successrsh check c0203 OK!

successrsh check c0205 OK!

successrsh check c0209 OK!

successrsh check c0213 OK!

successrsh check c0217 OK!

rsh check failed!


(21时58分09秒) 李拓: 我不太明白perl中的qx是怎么做的

对于qx那句话，我用脚本写的话，它会先跳到0203，然后跳回console
但是用perl写，则什么反应都没有……
至于python我不懂……

没办法更具体的为你解释了 
(21时59分11秒) LIZ: 呵呵，什么叫什么反应都没有，就是不输出什么另外的信息马
(22时00分35秒) LIZ: 因为 python里用那个os.system()会返回值的，0为正确执行，其他的就是有错误，但中间过程就不知道了
(22时03分12秒) 李拓: 对的，用qx什么都不会输出。

我把qx换成了一个我熟悉的语句，system

system好像是启动一个子shell来运行程序，最终返回子shell的状态。这样的话，可以顺利的在console上打印ABCD。

这个qx是如何实现，我就不清楚了 
(22时03分54秒) LIZ: 哦，呵呵，谢了，我猜也是有返回值的
(22时04分12秒) 李拓: 另外，在这个脚本里面我没发现那个\" \"的用途，应该是语句组，为了便于扩展使用的。 
(22时05分42秒) LIZ: 去掉也可以？
(22时06分36秒) 李拓: 如果只有这一句话，肯定可以！
在那个程序里，如果去掉我感觉也没有什么问题 
(22时07分01秒) LIZ: 哦，呵呵，麻烦你了，
(22时08分17秒) 李拓: ……你太客气了…… 

(21时57分14秒) lizzie: 在阿
(21时57分28秒) 刘刚: 你怎么换了阿
(21时57分29秒) 刘刚: 呵呵
(21时57分29秒) lizzie:  system("/usr/bin/rsh $nodes[$i] $command");
(21时57分47秒) 刘刚: 执行系统命令阿
(21时57分52秒) lizzie: 这里的system和刚才的qx是差不多
(21时57分53秒) lizzie: ？
(21时58分10秒) lizzie: 我QQ和MSN两个能同时用
(21时58分15秒) 刘刚: 是的
(21时58分41秒) 刘刚: 你怎么突然研究这个了阿
(21时58分49秒) 刘刚: 不是学的是ｐｙｔｈｏｎ吗？
(21时59分30秒) lizzie: 有什么区别，system返回不？
(21时59分39秒) lizzie: 值
(22时00分17秒) 刘刚: 这个不是很清楚的，那个ｑｘ的我就不知道他的返回值是怎么取的
(22时00分46秒) lizzie: 哦
(22时01分09秒) 刘刚: 你得查下资料，或者是做个小实验就可以知道了
(22时01分16秒) lizzie: 我就看了两个perl脚本，要写个pyhotn的
(22时01分40秒) 刘刚: 呵呵，利害阿
(22时01分59秒) lizzie: 没关系，这两个脚本都没处理他们的返回值，只是python里的有返回值要判断的，所以我问问
(22时04分37秒) 刘刚: 　你什么时候写好啊，发我看看，呵呵
(22时05分51秒) lizzie: 晕，早呢
(22时06分19秒) 刘刚: 应该很快的吧，学的多少了亚，写个小的应该不难吧
(22时07分56秒) lizzie: 因为还要有其他的东西，不过也不是很烦，就是不知道怎么在console机上执行也能带到节点机上一起执行



(22时02分14秒) lizzie: 忙不？
(22时02分28秒) 蔡俊杰: 不忙
(22时02分57秒) lizzie: rExecAll.pl里的有段
(22时03分06秒) lizzie: for($i=0;$i<@nodes;$i++){
                print "$nodes[$i]:\n";
                if($i==100){
                        #sleep(60);
                }
                if($i==200){
                        #sleep(60);
                }
(22时03分13秒) lizzie: 为什么要sleep
(22时03分44秒) 蔡俊杰: 注释掉了？
(22时04分10秒) lizzie: 不是不是，另一个里的
(22时04分31秒) lizzie: rshCheck.pl
(22时04分37秒) lizzie: 里的
(22时05分13秒) 蔡俊杰: 你知道，注释的原因大概是不希望rsh登陆各个节点太快
(22时05分18秒) lizzie: 我觉得可以不用阿，而且nodes就一个，也不用split吧，
(22时05分25秒) lizzie: 哦，
(22时05分43秒) 蔡俊杰: 你吧主要代码帖出来看看呢，我忘了是怎么样的了
(22时05分58秒) lizzie: 好的
(22时06分14秒) lizzie: foreach(@all) {
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
(22时06分43秒) lizzie: 写了点注释，就是那里sleep的和nodes的地方
(22时07分41秒) 蔡俊杰: 哦，我依稀记得，节点文件里面的记录是行写的吧，就是下面这个样子：

c0101
c0102
c0103
c0104
c0105
(22时07分45秒) 蔡俊杰: 对吧？
(22时08分01秒) lizzie: 恩
(22时08分09秒) lizzie: 就是一行只有一个
(22时08分20秒) 蔡俊杰: 实际上可以写成
c0101,c0102,c0103,c0104,c0105
(22时08分57秒) lizzie: 恩，这样就有用了
(22时09分12秒) lizzie: 那就是说可以去掉了，如果一行只有一个的话
(22时09分35秒) 蔡俊杰: @all中是什么内容？
(22时10分06秒) lizzie: 所有的节点，就是整个文件的内容
(22时10分49秒) 蔡俊杰: 恩，那就可以去掉
(22时11分01秒) lizzie: 还有阿
(22时12分12秒) lizzie: 我发现并行机上的python是2。2。2版本的，而我用的是2。5。1的，在并行机上有错误，比如说旧的上没有datetime这个模块的，
(22时12分26秒) 蔡俊杰: 恩
(22时12分32秒) 蔡俊杰: 那就需要自己安装一个
(22时13分13秒) 蔡俊杰: 建议你安装的时候使用普通用户安装
(22时13分32秒) lizzie: 哎
(22时13分38秒) 蔡俊杰: 有什么问题么？
(22时13分45秒) 蔡俊杰: 安装不会？
(22时15分11秒) lizzie: 也不是的
(22时15分36秒) 蔡俊杰:  你打算做什么用？
(22时15分59秒) lizzie: 阿？
(22时16分43秒) 蔡俊杰: 那有什么问题么？
(22时17分18秒) lizzie: 有点怕在并行机上装，刚问了李拓，说有点风险的，
(22时17分44秒) 蔡俊杰: 汗，你不用担心
(22时18分03秒) 蔡俊杰: 存在什么危险？
(22时18分30秒) 蔡俊杰: 大家都是学习嘛，你畏首畏尾怎么学的到东西，是吧
(22时18分41秒) lizzie: 对了，如果装的话，是直接覆盖的，还有要把以前的卸掉
(22时18分58秒) 蔡俊杰: 我不是说了嘛，你用普通用户安装
(22时19分03秒) lizzie: 哦，
(22时19分07秒) 蔡俊杰: 你知道 普通用户安装的，意思么？
(22时19分12秒) lizzie: 不用root
(22时19分13秒) 蔡俊杰: 不知道的话，我简单讲下
(22时19分35秒) lizzie: 好的
(22时19分51秒) 蔡俊杰: 在linux上每个用户都有自己的一个空间
(22时20分31秒) lizzie: 恩，那普通装了和系统本来就有的不会有什么冲突吗
(22时20分45秒) 蔡俊杰: 完全没有
(22时20分48秒) lizzie: 哦
(22时21分07秒) 蔡俊杰: 不要用windows的想法来考虑linux
(22时21分19秒) 蔡俊杰: 你用shengyan登陆系统
(22时21分28秒) lizzie: 哦
(22时21分37秒) 蔡俊杰: 那么你运行的所有命令在 .bash_profile中配置了
(22时21分48秒) 蔡俊杰: 这个配置就是环境变量
(22时21分49秒) lizzie: 哦，这个我知道
(22时21分52秒) 蔡俊杰: 恩
(22时22分23秒) 蔡俊杰: 那你知道这点的话，就足够了，你用普通用户安装python，和系统的那个版本一点关系都没有
(22时22分40秒) lizzie: 哦
(22时22分46秒) 蔡俊杰: 我在我自己的账户下，安装了4套apache版本，2套python版本，5套perl版本
(22时22分58秒) 蔡俊杰: 这就是linux
(22时23分01秒) lizzie: 呵呵，
(22时23分10秒) lizzie: 哦，
(22时23分19秒) 蔡俊杰: 我不会影响到其他任何用户
(22时23分59秒) lizzie: 好的，其实以前也听过，但没实际弄过，所以就觉得
(22时24分12秒) 蔡俊杰: 恩

