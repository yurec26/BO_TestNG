package org.example;

public class ArrayHelper {

    private final int maxArrayLen;

    public ArrayHelper(int maxArrayLen) {
        this.maxArrayLen = maxArrayLen;
    }

    public boolean isSorted(int[] array) {
        throwIfIllegalArgs(array);

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public void sortArray(int[] array) {
        throwIfIllegalArgs(array);

        int t;
        boolean isGood = false;
        while (!isGood) {
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i + 1]) {
                    t = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = t;
                } else {
                    isGood = true;
                }
            }
        }
    }

    public void throwIfIllegalArgs(int[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array should not be empty");
        }
        if (array.length > maxArrayLen) {
            throw new ArrayStoreException("Array len %s exceeded max len %s"
                    .formatted(array.length, maxArrayLen));
        }
    }

}
