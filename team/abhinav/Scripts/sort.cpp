#include <iostream>
#include <string.h>
using namespace std;
const int C = 15473;
int main()
{
  for (int i = 0; i < C; i++)
  {
    int n1,n2,n3,n4;
    cin>>n1>>n2>>n3>>n4;
    cout<<n1<<" ";
    if(n2>n3&&n2>n4)
    {
      cout<<n2<<" ";
      if(n3>n4)
        cout<<n3<<" "<<n4<<endl;
      else
        cout<<n4<<" "<<n3<<endl;
    }
    if(n3>n2&&n3>n4)
    {
      cout<<n3<<" ";
      if(n2>n4)
        cout<<n2<<" "<<n4<<endl;
      else
        cout<<n4<<" "<<n2<<endl;
    }
    if(n4>n2&&n4>n3)
    {
      cout<<n4<<" ";
      if(n2>n3)
        cout<<n2<<" "<<n3<<endl;
      else
        cout<<n3<<" "<<n2<<endl;
    }
  }
  return 0;
}
