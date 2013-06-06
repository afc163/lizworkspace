############################
NCBI Download 使用
############################

描述
======
这个小工具是实现从 `NCBI<http://www.ncbi.nlm.nih.gov`_ 上自动下载搜索结果并保存至本地文件夹. 

详细为:
  * 指定NCBI上数据库和搜索关键词;
  * 下载所有搜索结果, 保存2个版本的结果, 分别为原始html和精简后的纯txt结果.


运行及其配置
=============
  * 修改dist\config.ini中相关变量, db和term需要修改, pagesize和threadnum默认即可,
    * db为数据库名称, 可为如下值:protein, nuccore, nucest, ...具体可查看网站提供的数据库名字;
    * term为搜索关键词;
    * pagesize为一个页面上显示的搜索条目数;
    * threadnum为线程数.

  * 下载结果保存在dist\html中为原html文档, 使用对应id及其name命名, dist\txt中保存提取html文档后的纯文本
  * 注意: 在dist中有三个exe文件, 分别为:
    * download_no_stackless.exe: 这个内部没有使用多线程, 即顺序下载, 速度很慢, 但比较稳定;
    * download.exe: 这个是使用了多线程方式, 并发下载, 速度比上面个快, 但有时会出现异常;
    * download_native_thread.exe: 使用原始线程, 还算比较快;
    * 所以可以先尝试使用download.exe, 如果不行再使用download_no_stackless.exe, 另外可以运行多次不会将先前已保存的结果删除;
    * 有些网页可能无法下载, 屏幕上会有所显示.


开发工具及依赖关系
==================
  * Python 2.5.2 Stackless 3.1b3 060516
  * 使用 `py2exe<http://sourceforge.net/project/showfiles.php?group_id=15583&package_id=35995&release_id=640751>`_ 打包成可执行程序;
  * 依赖关系...


一些问题及扩展
===============
  * 在保存纯txt结果时, 目前保存了所有信息, 其中有很多信息是多余的, 但不知道哪些需要保留哪些是不需要的, 故统统保留, 待筛减;
  * 程序运行需要很长时间, 待改进.
