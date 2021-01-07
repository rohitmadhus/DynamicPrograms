public class CoinChangeMax {
  //back track for every subproblem to find the number of coins
    public static int coinCount(int arr[][],int total,int coin,int coinIndex){
        int i=0;
        while(true){
            total=total-coin;
            if(total>0){
                i++;
                if(arr[coinIndex][total]==arr[coinIndex-1][total])
                break;
            }else 
            break;      
        }
        return i;
    }

    public static void printCoin(int arr[][],int coin[],int total){
        int coinIndex = coin.length-1;
        if(arr[coinIndex][total]==0){
            System.out.println("No Solution");
            return;
        }
        while(true){
            //top value same as bottom value
            //need to traverse up zero usage of current coin
            while(coinIndex>=1 && arr[coinIndex][total]==arr[coinIndex-1][total]){
                coinIndex--;
            }
            //travering left as one coin is used 
            int count=0;
            System.out.print(coin[coinIndex]+" Rs -> ");
            while(total>0 && arr[coinIndex][total]-1==arr[coinIndex][total-coin[coinIndex]]){
                count++;
                //System.out.print(coin[coinIndex]+" - ");
                total=total-coin[coinIndex];
                if(total<=0)break;
            }
            System.out.println(count);
            if(total<=0)break;
        }
    }

    public static void coinChangeNew(int total,int coin[],int count[]){   
        int arr[][] = new int[coin.length][total+1];
        //As 1st row is the subproblem with 1 coin we can dierectly assign the value
        arr[0][0]=0;
        for(int i=1;i<total+1;i++){
            if(count[0]>0){
                if(i%coin[0]==0){      
                    count[0]--;
                    arr[0][i]=i/coin[0];
                }
                else{
                    arr[0][i]=0;
                }
            }

        }
        //already strored value for i=0 in the above loop
        for(int i=1;i<coin.length;i++){
            for(int j=0;j<total+1;j++){
                //if coin value is less than subproblem total dierectly copy the value just above it
                //main if rest logic in else case
                if(j<coin[i]){
                    arr[i][j]=arr[i-1][j];
                }
                else{
                    //subproblem with sum less than the current coin
                    int temp1 = arr[i][j-coin[i]]+1;
                    //subproblem with sum without current coin
                    int temp2=  arr[i-1][j];

                    //if both values are zero (1 because we are adding temp1 with 1)
                    if(temp1==1&&temp2==0){
                        //case when its 1st attempt to use coin the current coin
                        //no other coin before satisfies this subproblem
                        if(j==coin[i]){   
                                arr[i][j]=1;
                                continue;            
                        } 
                        //no solution with the current coins 
                        else{
                            arr[i][j]=0;
                            continue;
                        }
                    }
                    //if cions used are more without using the current coin
                    //then use the above solution
                    if(temp2>temp1){
                      arr[i][j]=temp2;  
                    }else{
                    //we are using current coin need to backtrack how many coins used 
                    //if its below current coin count use the current coin
                        if(coinCount(arr,j,coin[i],i)<=count[i])
                        arr[i][j]=temp1;
                        else
                        arr[i][j]=temp2;
                    }
                
                }
            }
        }
        //  To show the 2-D array contructed by dividing into sub problems
        //     for(int j=0;j<coin.length;j++){
        //         System.out.print(coin[j]+" -> ");
        //         for(int i=0;i<total+1;i++){
                    
        //         System.out.print(arr[j][i]+" ");
        //     }
        //     System.out.println();
        // }

        printCoin(arr,coin,total);
    }



    public static void main ( String args[] ) {
        int total = 511;
        int coins[] = {3,9,13,27};
        int count[] = {10,10,10,10};
        //int coins[] = {27,13, 9, 3};
        coinChangeNew(total,coins,count);
    }
}