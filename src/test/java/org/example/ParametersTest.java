package org.example;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class ParametersTest {

    // чтобы переменные были видны надо запускать именно через хмл файл.
    @Test()
    @Parameters("RRR")
    public void testArrayIsSorted(@Optional("Zdarova default") String RRR) {
        System.out.println(RRR);
    }
}
