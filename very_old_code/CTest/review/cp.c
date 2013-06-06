#include <iostream>


class CBase{
public:
    CBase(void);
    virtual ~CBase(void);
};

int main()
{
       int * a;
       std::cout<<a<<std::endl;
       a++;
       std::cout<<a<<std::endl;
}
