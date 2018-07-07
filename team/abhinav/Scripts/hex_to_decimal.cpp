#include <iostream>
#include <string.h>
#include <stdlib.h>
#include <cstdlib>
using namespace std;
const int C = 46419;
int main()
{
  string in[21];
  for (int i = 0; i < C; i++)
  {
      for (int j = 0; j < 21; j++)
      {
        cin>>in[j];
      }
      string sn=in[0]+in[1];
      string p=in[2]+in[3];
      int num1 = (int)strtol(sn.c_str(), NULL, 16);
      int num2 = (int)strtol(p.c_str(), NULL, 16);
      cout<<num1<<" "<<num2<<endl;
  }
  return 0;
}
