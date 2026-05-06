package ecosystem.entities.animals;

import ecosystem.behaviors.HerbivoreBehavior;
import ecosystem.behaviors.RandomMovement;
import ecosystem.core.Environment;
import ecosystem.core.Position;
import ecosystem.interfaces.Reproducible;

import java.util.Random;

@SuppressWarnings("all")

public class Rabbit extends Animal implements Reproducible
{

    // ============ Fields ============
    private double reproduceChance = 0.3f;


    public Rabbit(Position position)
    {
        super(position, 'R', true,50, 100, 0, new HerbivoreBehavior(), new RandomMovement());
    }

    // ============ Constructors ============


    //Interface Methods

    //Actable
    @Override
    public void act(Environment env)
    {
        super.act(env);

        if(this.getM_energy() > 30) //If Energy is Bigger then 30 Try To Reproduce
            reproduce(env);
    }

    //Reproducible
    @Override
    public boolean reproduce(Environment env)
    {
        Random generator = new Random();
        double chance = generator.nextDouble(); // gives number between 0.0 and 1.0

        if(chance < reproduceChance) // Add A Rabbit
            return true;

        return false;

    }

}
