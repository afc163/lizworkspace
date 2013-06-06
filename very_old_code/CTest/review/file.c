#include <stdio.h>
#include <string.h>

/*******************
`读取一个文本文件`
*******************/
void read_text_file(void)
{
    char ch;
    FILE * fp;
    fp = fopen("test", "r");
    ch = fgetc(fp);
    while (ch != EOF)
    {
        putchar(ch);
        ch = fgetc(fp);
    }
}

/*******************
`顺序读取一个二进制文件`
*******************/
void read_text_file(void)
{
    char ch;
    FILE * fp;
    fp = fopen("test", "rb");
    while (!feof(fp)) //`feof值为0时表示未到文件结束, 读入一个字节的数据赋给整型变量c, 遇到文件结束时, feof为1. 这种方法也适用于文本文件.`
    {
        ch = fgetc(fp);
        //...
    }
    fclose(fp);
}

/*******************
`自定义的getw函数`
*******************/
int getw(FILE * fp)
{
    char * ch;
    int i;
    s = (char * )&i;
    s[0] = getc(fp);
    s[1] = getc(fp);
    return i;
}

/*******************
`自定义的putw函数`
*******************/
int putw(int i, FILE * fp)
{
    char * s;
    s = (char * )&i;
    putc(s[0],fp);
    putc(s[1],fp);
    return i;
}

/*******************
`在系统不提供fread和fwrite时, 编写任何类型的读写文件函数, 这里putfloat, 将一个浮点数写入文件`
*******************/
int putfloat(float num, FILE * fp)
{
    char * s;
    int count;
    s = (char * )&num;
    for (count=0; count<4; count++) putc(s[count], fp);
    return 1;
}
