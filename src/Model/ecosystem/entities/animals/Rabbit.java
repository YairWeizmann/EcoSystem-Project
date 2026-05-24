package Model.ecosystem.entities.animals;

import Model.ecosystem.behaviors.HerbivoreBehavior;
import Model.ecosystem.behaviors.RandomMovement;
import Model.ecosystem.core.Environment;
import Model.ecosystem.core.Position;
import Model.ecosystem.interfaces.EdibleByHerbivore;
import Model.ecosystem.interfaces.Reproducible;

import java.util.Random;

public class Rabbit extends Animal implements Reproducible , EdibleByHerbivore
{

    // ============ Fields ============
    private double reproduceChance = 0.3;


    // ============ Constructors ============
    public Rabbit(Position position) {
        super(position, 'R', true,EntityType.Rabbit , 50, 50, 0, new HerbivoreBehavior(), new RandomMovement());
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
                Position freePos = env.getFreeNearbyPos(this.getM_position());
                if(freePos != null)
                {
                    //Create a new baby rabbit -> new Rabbit(emptyPosition)
                    Rabbit babyRabbit = new Rabbit(freePos);
                    env.addEntity(babyRabbit);// Add the new baby rabbit to the environment -> env.addEntity(babyRabbit)

                    System.out.println("A new rabbit was born!");
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
