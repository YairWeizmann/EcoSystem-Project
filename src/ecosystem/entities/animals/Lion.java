package ecosystem.entities.animals;

import ecosystem.behaviors.FeedingBehavior;
import ecosystem.behaviors.MovementStrategy;
import ecosystem.core.Position;

public class Lion extends Animal
{

    // ============ Fields ============
    // filed handle in animal

    // ============ Constructors ============
    public Lion(Position position,
                FeedingBehavior eatBehavior, MovementStrategy animalMove)
    {
        super(position, 'L', true, 100, 100, 0, eatBehavior, animalMove);

    }
}

