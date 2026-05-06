package ecosystem.entities.animals;

import ecosystem.behaviors.CarnivoreBehavior;
import ecosystem.behaviors.ChaseMovement;
import ecosystem.behaviors.FeedingBehavior;
import ecosystem.behaviors.MovementStrategy;
import ecosystem.core.Environment;
import ecosystem.core.Position;

public class Lion extends Animal
{

    // ============ Fields ============


    // ============ Constructors ============
    public Lion(Position position)
    {
        super(position, 'L', true, 100, 100, 0, new CarnivoreBehavior(), new ChaseMovement());
    }



    // ============ Interface Methods ============



}

