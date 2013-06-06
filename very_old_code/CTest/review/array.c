#include <stdio.h>
#include <string.h>

/*******************
`统计s数组中串的实际长度`
*******************/
int get_string_real_count(char *ss)
{
    char * ps=ss;
    while (*ps != '\0') ps++;
    return ps-ss;
}

/*******************
`字符串与指针`
*******************/
int string(void)
{
    char * string="some string"; // `这个只是将"some string"的首字符地址赋给string指针, 其指向的是一字符类型. `
    char * string1;
    char str[20]={"some string"}; // `数组初始化, 之后就能整体赋值了. str[]="some string"是不对的.`
    //*string1 = "some string"; // `这才是真正的将字符串赋给string1指针. 不过测试出来不对阿...`
    printf("%s\n", string);
    printf("%d\n", sizeof(string));
    //printf("%s\n", string1);
    
    char * a, str2[10];
    a = str2;
    scanf("%s", a);  //`这样做比较安全, 比不加a = str2;好, 因为光光char * a中a的值是随机的, 有可能指向已用的内存段.`
    
}

// `调用时可以sub(f1, f2);这在每次调用sub函数时, 要调用的函数不是固定的, 就很方便了.`
void sub(int (* x1)(int), int (* x2)(int, int)) //`x1指向的函数有一个整型形参, x2指向的函数有两个整型形参`
{
    int a, b, i, j;
    a = (* x1)(i);
    b = (* x2)(i,j);
}

//`在举个函数指针的例子`
int max(int, int); //`两个数的较大者    先作声明, 因为在process中使用max作为形参, 表明max是函数, 而不是普通变量.`
int min(int, int); //`两个数的较小者`
int add(int, int); //`两个数的和`

//`现在定义一个process, 要求可以实现不同功能...调用时process(10, 10, max), process(10, 10, add);`
void process(int x, int y, int (* func)(int, int))
{
    int result;
    result = (*func)(x,y);
    printf("%d\n", result);
}

void mainmain(int argc, char * argv[]) // `这个argv值可变, 而不是像之前的数组名常量, 因为形参是变量`
{
    while (argc-->1) printf("%s\n", *++argv);
    while (--argc>0) printf("%s%c", *++argv, (argc>1)?' ':'\n');
}
int main(void)
{
    char s1[10];
    int a[10], i, *p;
    
    /*scanf("%s", s1);
    printf("%d\n", get_string_real_count(s1));
    
    //`指针变量增1运算,,, 运行速度会比(a+i)等快`
    for (i=0; i<10; i++) scanf("%d", (a+i));
    for (p=a; p<(a+10); p++) printf("%d", *p);
    p = a;
    for (i=0; i<10; i++) printf("%d", *p++); */
    string();
    printf("\n");
    return 0;
}
