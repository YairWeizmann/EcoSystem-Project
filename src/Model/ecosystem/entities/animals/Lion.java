package Model.ecosystem.entities.animals;

import Model.ecosystem.behaviors.Animals.CarnivoreBehavior;
import Model.ecosystem.behaviors.Animals.ChaseMovement;
import Model.ecosystem.core.Position;
import Model.ecosystem.interfaces.EcosystemCommand;
import Model.ecosystem.interfaces.EdibleByCarnivore;

import java.util.concurrent.BlockingQueue;

public class Lion extends Animal implements EdibleByCarnivore
{

    // ============ Constructors ============
    public Lion(Position position, BlockingQueue<EcosystemCommand> commandQueue)
    {
        super(position, 'L', true,commandQueue ,EntityType.Lion,100, 400, 0, new CarnivoreBehavior(),new ChaseMovement() );
    }

}

