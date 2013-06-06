#include <iostream.h>

void fn()
{
    int & a = new int(2); // `a不是指针`
    int * pInt = new int;
    if(pInt == NULL){
        return;
    }
    int & rInt = *pInt;
    //...
    delete &pInt;
    delete &a;
}

class SleeperSofa: public Bed, public Sofa
{
    public:
        SleeperSofa():Sofa(), Bed(){}
};

class Sofa: virtual public Furniture //`虚拟继承`
{
    public:
        Sofa(){}
};
void main()
{
    int a, b;
    cin>>a>>b;
    cout<<"hi~"<<endl;
}
