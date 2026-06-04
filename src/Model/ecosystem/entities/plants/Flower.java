package Model.ecosystem.entities.plants;

import Model.ecosystem.core.Environment;
import Model.ecosystem.core.Position;
import Model.ecosystem.interfaces.EcosystemCommand;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Flow;

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
    public Flower(Position position, char symbol, boolean is_alive, BlockingQueue<EcosystemCommand> commandQueue, double energy, double max_energy, double age,
                  double growthRate, double reproductionChance)
    {
        super(position, symbol, is_alive,commandQueue,EntityType.Flower ,energy, max_energy = 70, age, growthRate = 5, reproductionChance = 0.2f);
    }
    public Flower(Position position,BlockingQueue<EcosystemCommand> commandQueue)
    {
        super(position, symbol, is_alive,commandQueue,EntityType.Flower ,energy, max_energy, age, growthRate, reproductionChance);
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

    @Override
    public void act(Environment env)
    {
        super.act(env);

        boolean sucessReproduction = reproduce(env);

        if(sucessReproduction)
        {
            int distanceFromFlower = 2;
            Position newPos = env.getFreeNearbyPos(this.getM_position(),distanceFromFlower);
            if(newPos == null)
                return;

            Flower newFlower = new Flower(newPos,getM_commandQueue());
            env.addEntity(newFlower);

        }
    }
}
