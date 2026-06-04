package Model.ecosystem.entities.animals;
import Model.ecosystem.behaviors.Animals.EscapeMovement;
import Model.ecosystem.behaviors.Animals.HerbivoreBehavior;
import Model.ecosystem.core.Position;
import Model.ecosystem.interfaces.EcosystemCommand;
import Model.ecosystem.interfaces.EdibleByHerbivore;

import java.util.concurrent.BlockingQueue;

public class Deer extends Animal implements EdibleByHerbivore
{
    // ============ Constructors ============
    public Deer(Position position , BlockingQueue<EcosystemCommand> commandQueue)
    {
        super(position, 'D', true,commandQueue,EntityType.Deer ,70, 70, 0,new HerbivoreBehavior(),
                new EscapeMovement());
    }

}
