#include <stdio.h>
#include <string.h>

/*******************
`模拟strcat(s1, s2)功能`
*******************/
char * mystrcat(char s1[], char s2[])
{
    int i = 0, j = 0;
    while (s1[i] != '\0') i++; //while (s[i]) i++;
    while ((s1[i]=s2[j]) != '\0') 
    {
        i++;
        j++;
    }
    return s1;
}

/*******************
`模拟strcpy(s1, s2)功能`
*******************/
char * mystrcpy(char s1[], char s2[])
{
    int i;
    for (i=0; (s1[i]=s2[i]) != '\0'; i++); // for (i=0; (s1[i]=s2[i]); i++);
    return s1;
}

/*******************
`字符串反转功能`
*******************/
char * reverse(char ss[])
{
    int i, j, c;
    for (i=0, j=strlen(ss)-1; i<j; i++, j--)
    {
        c = ss[i];
        ss[i] = ss[j];
        ss[j] = c;
    }
    return ss;
}

/*******************
`统计字符串中单词个数, 以空格为分隔符`
`碰到空格后的第一个非空格字符, 形如 \_ A, 表示出现一个单词`
*******************/
int get_words_count(char ss[])
{
    int i, num=0, word=0;
    for (i=0; ss[i]; i++)
    {
        if (ss[i] == '\40') word = 0;
        else if (word == 0)
        {
            word = 1;
            num++;
        }
    }
    return num;
}

int main(void)
{
    char s1[10], s2[20];
    //scanf("%s", s1);
    //scanf("%s", s2);
    //puts(mystrcat(s1, s2));
    //puts(mystrcpy(s1, s2));
    //puts(reverse(s2));
    //printf("%d\n", get_words_count(gets(s2)));
    printf("%d %d\n", 12/7, 12%7);
    return 0;
}
