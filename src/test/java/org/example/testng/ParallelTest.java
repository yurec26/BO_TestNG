package org.example.testng;

import org.example.ArrayHelper;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class ParallelTest {

    // параллель внутри итераций одного метода и параллель методов в классе - разные вещи !
    @Test(dataProvider = "unsorted",
            invocationCount = 5,
            threadPoolSize = 2)
    public void testArrayIsSorted(int[] array) {
        ArrayHelper arrayHelper = new ArrayHelper(30);
        arrayHelper.sortArray(array);
        assertTrue(arrayHelper.isSorted(array),
                "Array should be sorted");
        System.out.println(Thread.currentThread().getName() + " Parallel test 1");
    }

    @DataProvider(name = "unsorted",
            parallel = true)
    public Object[] providerArraysUnsorted() {
        return new Object[]{
                new int[]{1, 2, 44, 4, 5},
                new int[]{3, 5, 67, 23, 45},
                new int[]{0, 0, 0, 0, 2, 0, 0}
        };
    }
}
