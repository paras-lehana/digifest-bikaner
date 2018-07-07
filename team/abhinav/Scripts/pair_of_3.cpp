#include <iostream>
#include <string.h>
using namespace std;
const int C = 40291;
string in[22][C];
int main() {
  for (int i = 1; i <= C; i++) {
    for (int j = 0; j < 22; j++)
      cin>>in[j][i-1];
  }
  /*for (int i = 1; i <= C; i++) {
    for (int j = 0; j < 21; j++)
      cout<<in[j][i-1]<<" ";
	cout<<endl;
}*/
  for (int i = 0; i < C-2; i++) {
    if(in[1][i]==in[1][i+1]&&in[1][i+1]==in[1][i+2])
    {
      for (int j = 0; j < 21; j++)
      {
        cout<<in[j][i]<<" ";
      }
      cout<<endl;
      for (int j = 0; j < 21; j++)
      {
        cout<<in[j][i+1]<<" ";
      }
      cout<<endl;
      for (int j = 0; j < 21; j++)
      {
        cout<<in[j][i+2]<<" ";
      }
      cout<<endl;
    }
  }
  return 0;
}
