package Model.ecosystem.entities.animals;

import Model.ecosystem.behaviors.Animals.CarnivoreBehavior;
import Model.ecosystem.behaviors.Animals.ChaseMovement;
import Model.ecosystem.core.Position;
import Model.ecosystem.interfaces.EdibleByCarnivore;

public class Lion extends Animal implements EdibleByCarnivore
{

    // ============ Constructors ============
    public Lion(Position position)
    {
        super(position, 'L', true, EntityType.Lion,100, 400, 0, new CarnivoreBehavior(),new ChaseMovement() );
    }

}

