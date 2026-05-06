package ecosystem.entities.animals;

import ecosystem.behaviors.HerbivoreBehavior;
import ecosystem.behaviors.RandomMovement;
import ecosystem.core.Environment;
import ecosystem.core.Position;
import ecosystem.interfaces.Reproducible;

import java.util.Random;

public class Rabbit extends Animal implements Reproducible
{

    // ============ Fields ============
    private double reproduceChance = 0.3;


    // ============ Constructors ============
    public Rabbit(Position position) {
        super(position, 'R', true, 50, 50, 0, new HerbivoreBehavior(), new RandomMovement());
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
                // TODO: 1. Get an empty position near the rabbit from the environment
                // TODO: 2. Create a new baby rabbit -> new Rabbit(emptyPosition)
                // TODO: 3. Add the new baby rabbit to the environment -> env.addEntity(babyRabbit)
                System.out.println("A new rabbit was born!");
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
}
