package ecosystem.entities.animals;

import ecosystem.behaviors.CarnivoreBehavior;
import ecosystem.behaviors.ChaseMovement;
import ecosystem.core.Position;
import ecosystem.interfaces.EdibleByCarnivore;

public class Lion extends Animal implements EdibleByCarnivore
{

    // ============ Constructors ============
    public Lion(Position position)
    {
        super(position, 'L', true, 100, 400, 0, new CarnivoreBehavior(),new ChaseMovement() );
    }

}

