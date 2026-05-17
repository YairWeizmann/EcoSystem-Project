package ecosystem.entities.plants;

import ecosystem.core.Environment;
import ecosystem.core.Position;

import java.util.Random;

@SuppressWarnings("all")

public class Flower extends Plant
{
    // ===================== Fields =====================
    private final static char symbol = 'F';
    private final static boolean is_alive = true;
    private final static int energy = 70;
    private final static int max_energy = 140;
    private final static int age = 0;
    private final static double growthRate = 5f;
    private final static double reproductionChance = 0.2f;

    // ===================== Constructors =====================
    public Flower(Position position, char symbol, boolean is_alive, double energy, double max_energy, double age,
                  double growthRate, double reproductionChance)
    {
        super(position, symbol, is_alive, energy, max_energy = 70, age, growthRate = 5, reproductionChance = 0.2f);
    }
    public Flower(Position position)
    {
        super(position, symbol, is_alive, energy, max_energy, age, growthRate, reproductionChance);
    }

    // ===================== Interfaces Methods =====================
    //Reproduceable
    @Override
    public boolean reproduce(Environment env)//Makes a new flowers (1-3) in a distance of 2 from this current Flower
    {
        Random randomGenerator = new Random();
        float chance = randomGenerator.nextFloat(0.0f,1f);

        if(reproductionChance <= chance)
            return true;

        return false;
    }
}
