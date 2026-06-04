package Model.ecosystem.entities.animals;

import Model.ecosystem.behaviors.Animals.HerbivoreBehavior;
import Model.ecosystem.behaviors.Animals.RandomMovement;
import Model.ecosystem.core.Environment;
import Model.ecosystem.core.Position;
import Model.ecosystem.interfaces.EcosystemCommand;
import Model.ecosystem.interfaces.EdibleByHerbivore;
import Model.ecosystem.interfaces.Reproducible;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Rabbit extends Animal implements Reproducible , EdibleByHerbivore
{

    // ============ Fields ============
    private double reproduceChance = 0.3;
    // ============ Constructors ============
    public Rabbit(Position position , BlockingQueue<EcosystemCommand> commandQueue)
    {
        super(position, 'R', true,commandQueue , EntityType.Rabbit, 50, 50, 0, new HerbivoreBehavior(), new RandomMovement());
    }


    // ============ Interface Methods ============
    // Actable
    @Override
    public void act(Environment env)
    {
        // 1. Execute normal animal actions (aging, sensing, eating, moving)
        super.act(env);

        // 2. Verify the rabbit is still alive after the regular act and has enough energy
        if (this.getIs_alive() && this.getM_energy() > 30)
        {

            // Store the result of the reproduction attempt
            boolean success = reproduce(env);

            if (success)
            {
                //Try To Get an empty position near the rabbit from the environment

                int distanceFromRabbit = 1;
                Position freePos = env.getFreeNearbyPos(this.getM_position() , distanceFromRabbit);
                if(freePos != null)
                {
                    //Create a new baby rabbit -> new Rabbit(emptyPosition)
                    Rabbit babyRabbit = new Rabbit(freePos,getM_commandQueue());
                    Thread newRabbitThread = new Thread(babyRabbit);
                    env.addEntity(babyRabbit);// Add the new baby rabbit to the environment -> env.addEntity(babyRabbit)

                    newRabbitThread.start();
                    System.out.println("A new rabbit was born!");
                    return;
                }

                System.out.println("no Near By Free Positions");

            }
        }
    }

    // Reproducible
    @Override
    public boolean reproduce(Environment env)
    {
        Random generator = new Random();
        double chance = generator.nextDouble(); // gives number between 0.0 and 1.0

        if (chance < reproduceChance)
            return true;

        return false;
    }


    // ============ Helper Methods ============
}
