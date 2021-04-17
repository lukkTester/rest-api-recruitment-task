package com.lukk.utilities;

import java.util.List;

public class CalculationUtils {

    public static float sumListElements(List<Number> numberList) {

        float sum = 0;

        for (Number element : numberList)

            sum = sum + element.floatValue();

        return sum;
    }
}
