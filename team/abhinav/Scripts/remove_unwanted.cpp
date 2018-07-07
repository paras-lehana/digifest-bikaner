#include <iostream>
#include <string.h>
using namespace std;
int main() {
  const int C = 40291;
  string in[36];
  for (int i = 1; i <= C; i++) {
    for (int j = 0; j < 36; j++)
      cin>>in[j];
    for (int j = 14; j < 36; j++)
      cout<<in[j]<<" ";
    cout<<endl;
  }
  return 0;
}
