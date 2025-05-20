class merge_short {
  public static void main(String[] args){
    MergeSort sorting = new MergeSort();

    int[] sorted = sorting.sort(new int[]{2, 20, 200, 1, 5});

    for(int value : sorted){
      System.out.println(value);
    }

  }
}

class MergeSort {
  public int[] sort(int[] data){
    return this.split(data);
  }
  public int[] split(int[] data){
    if(data.length <= 1) return data;

    int middle = data.length / 2;

    int[] left = new int[middle];
    int[] right;

    if(data.length % 2 == 0){
      right = new int[middle];
    } else {
      right = new int[middle + 1];
    }

    for(int i = 0; i < left.length; i++){
      left[i] = data[i];
    }

    for(int i = 0; i < right.length; i++){
      right[i] = data[middle + i];
    }

    left = split(left);
    right = split(right);

    return merge(left, right);
  }

  public int[] merge(int[] left, int[] right){
    int[] result = new int[left.length + right.length];
    int leftIndex = 0;
    int rightIndex = 0;
    int index = 0;

    while(leftIndex < left.length && rightIndex < right.length){
      if(left[leftIndex] > right[rightIndex]){
        result[index] = left[leftIndex];
        leftIndex++;
      } else {
        result[index] = right[rightIndex];
        rightIndex++;
      }
      index++;
    }

    while(leftIndex < left.length){
      result[index] = left[leftIndex];
      leftIndex++;
      index++;
    }

    while(rightIndex < right.length){
      result[index] = right[rightIndex];
      rightIndex++;
      index++;
    }

    return result;

  }
}
