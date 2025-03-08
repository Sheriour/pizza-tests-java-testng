package utils;

import java.util.Random;

public class Utils
{
    static Random generator = new Random();

    /**
     * @param min   Minimum value
     * @param max   Maximum value
     * @return      Random number between min and max, inclusive
     */
    public static int getRandomNumberBetween(int min, int max){
        return generator.nextInt(max - min + 1) + min;
    }
}
