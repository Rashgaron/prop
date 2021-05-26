package Domain.utility;

import java.util.Random;

/**
 * @author Daniel Rodriguez
 */

public class RandomNumber implements IRandomNumber{
    public int getRandomNumber(){
        return new Random().nextInt(6);
    }
}
