class pratikum2_2 {
  public static void main(String[] args){
    CountingSort sorter = new CountingSort();
    int[] sorted = sorter.sort(new int[] {3,1,2,5,10,0});


    for(int number : sorted){
      System.out.println(number);
    }


  }
}

class CountingSort{
  public int[] sort(int[] data){
    int max = this.getMax(data);

    int[] countArray = new int[max + 1];

    for(int number : data){
      countArray[number]++;
    }

    for(int i =1; i < countArray.length; i++){
      countArray[i] += countArray[i - 1];
    }

    int[] outputArray = new int[data.length];

    for(int i = outputArray.length - 1;i >=0; i--){
      outputArray[countArray[data[i]] - 1] = data[i];
      countArray[data[i]]--;
    }

    int[] revered = new int[outputArray.length];

    for(int i = 0; i < data.length; i++){
      revered[outputArray.length - i - 1] = outputArray[i];
    }

    return revered;

  }
  public int getMax(int[] data){
    int max = 0;
    for(int number: data){
      if(max < number){
        max = number;
      }
    }
    return max;
  }
}
