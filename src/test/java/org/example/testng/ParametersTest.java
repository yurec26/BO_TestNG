package org.example.testng;

import org.example.ArrayHelper;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class ParametersTest {

    // чтобы переменные были видны надо запускать именно через хмл файл.
    @Test()
    @Parameters("RRR")
    public void testArrayIsSorted(@Optional("Zdarova default") String RRR) {
        // System.out.println(RRR);
        System.out.println("ParametersTest 1 " + Thread.currentThread().getName() + " " + RRR);
    }

    @Test
    public void testSame() {
        ArrayHelper arrayHelper = new ArrayHelper(5);
        ArrayHelper arrayHelper1 = arrayHelper;
        assertSame(arrayHelper, arrayHelper1,
                "reference same corporation");
        System.out.println("ParametersTest 2 " + Thread.currentThread().getName());
    }

    @Test
    public void testNotSame() {
        ArrayHelper arrayHelper = new ArrayHelper(5);
        ArrayHelper arrayHelper1 = new ArrayHelper(5);
        assertNotSame(arrayHelper, arrayHelper1,
                "reference NOT same corporation");
        System.out.println("ParametersTest 3 " + Thread.currentThread().getName());
    }
}
