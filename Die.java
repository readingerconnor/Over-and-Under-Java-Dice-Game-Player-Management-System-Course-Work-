import java.util.Random;


public class Die{

    // Reuse one Random object instead of creating one for every roll.
    private Random rand = new Random();
    private int value;

    public int roll() {
        // A single die returns a value from 1 through 6.
        value = rand.nextInt(6) + 1;
        return value;
    }
}
