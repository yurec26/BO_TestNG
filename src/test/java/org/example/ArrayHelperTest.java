package org.example;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;


public class ArrayHelperTest {


    @Test(dataProvider = "sorted")
    public void testArrayIsSorted(int[] array) {
        ArrayHelper arrayHelper = new ArrayHelper(30);
        assertTrue(arrayHelper.isSorted(array),
                "Array should be sorted");
    }

    @Test(dataProvider = "unsorted")
    public void testArrayIsUnsorted(int[] array) {
        ArrayHelper arrayHelper = new ArrayHelper(30);
        assertFalse(arrayHelper.isSorted(array),
                "Array should be unsorted");
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Array should not be empty")
    public void testEmptyArray() {
        ArrayHelper arrayHelper = new ArrayHelper(30);
        int[] array = new int[0];
        arrayHelper.isSorted(array);
    }

    @Test(dataProvider = "sorted",
            expectedExceptions = ArrayStoreException.class,
            expectedExceptionsMessageRegExp = "Array len .+ exceeded max len .+")
    public void testExceededArray(int[] array) {
        ArrayHelper arrayHelper = new ArrayHelper(3);
        arrayHelper.isSorted(array);
    }


    @DataProvider(name = "sorted")
    public Object[] providerArraysSorted() {
        return new Object[]{
                new int[]{1, 2, 3, 4, 5},
                new int[]{3, 5, 67, 232, 1245},
                new int[]{0, 0, 0, 0, 0, 0, 0}
        };
    }

    @DataProvider(name = "unsorted")
    public Object[] providerArraysUnsorted() {
        return new Object[]{
                new int[]{1, 2, 44, 4, 5},
                new int[]{3, 5, 67, 23, 45},
                new int[]{0, 0, 0, 0, 2, 0, 0}
        };
    }
}
