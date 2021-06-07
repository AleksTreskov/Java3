package Homework;

public class Methods {




    public int[] lastFourArray(int[] arr) {
        boolean isFourInArr = false;
        int numberOfLastFour = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 4) {
                isFourInArr = true;
                numberOfLastFour = i + 1;
            }
        }
        if (!isFourInArr)
            throw new RuntimeException();
        int[] newArr = new int[arr.length - numberOfLastFour];
        System.arraycopy(arr, numberOfLastFour, newArr, 0, arr.length - numberOfLastFour);

        return newArr;
    }

    public  boolean oneOrFour(int[] arr) {
        boolean countOne = false;
        boolean countFour = false;
        for (int j : arr
        ) {
            if (j != 4 && j != 1)
                return false;
            if (j == 4) {
                countFour =true;
            } else
                countOne=true;
        }
        return countFour && countOne;
    }
}

