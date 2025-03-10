package utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
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

    /**
     * Will wait via thread.sleep for a number of seconds.
     *
     * @param secs  Seconds to wait
     */
    public static void waitForSeconds(int secs){
        try {
            Thread.sleep(secs * 1000L);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
