package ecosystem.entities.plants;

import ecosystem.core.Environment;
import ecosystem.core.Position;

@SuppressWarnings("all")

public class Flower extends Plant
{
    // ===================== Constructors =====================
    public Flower(Position position, char symbol, boolean is_alive, double energy, double max_energy, double age,
                  double growthRate, double reproductionChance)
    {
        super(position, symbol, is_alive, energy, max_energy = 70, age, growthRate = 5, reproductionChance = 0.2f);
    }

    // ===================== Interfaces Methods =====================
    //Reproduceable
    @Override
    public boolean reproduce(Environment env)
    {
        //Makes a new flowers (1-3) in a distance of 2 from this current Flower
        return false;
    }
}
