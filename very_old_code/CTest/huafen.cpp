#include <iostream>
#include <iterator>


void     Generate_Set_Partition( int n ){
    int  *List=new int[n], *T=new int[n], i, j;
    for( i=0; i<n; ++i )    List[i]=0, T[i]=1;
    while( 1 ){
        copy( List, List+n, ostream_iterator<int>(cout," ") );
        cout<<endl;
        for( i=n-1; List[i]==T[i]; --i );
        if( i==0 )    return;
        ++List[i];
        for( j=i+1; j<n; ++j )
            List[j]=0, T[j]=List[i]+1;
    }
    delete []List; delete[]T;
}

int main(void)
{
    Generate_Set_Partition(4);
}
