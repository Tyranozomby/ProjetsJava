package utils;

public class Random {

    /**
     * Static method to try a chance.
     *
     * @param probability the probability of success
     * @return the result of the probability
     */
    public static boolean tryChance(double probability) {
        if (probability < 0 || probability > 1) {
            throw new IllegalArgumentException("Probability must be between 0 and 1");
        }
        return Math.random() < probability;
    }

    /**
     * Static method to get a random integer between min and max (included).
     *
     * @param min minimum value
     * @param max maximum value
     * @return min random integer between min and max
     */
    public static int getRandomInt(int min, int max) {
        if (min > max) {
            int temp = min;
            min = max;
            max = temp;
        }
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
