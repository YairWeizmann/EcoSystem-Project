package ecosystem.entities.animals;
import ecosystem.behaviors.EscapeMovement;
import ecosystem.behaviors.HerbivoreBehavior;
import ecosystem.core.Position;
import ecosystem.interfaces.EdibleByHerbivore;

public class Deer extends Animal implements EdibleByHerbivore
{
    // ============ Constructors ============
    public Deer(Position position)
    {
        super(position, 'D', true, 70, 70, 0,new HerbivoreBehavior(),
                new EscapeMovement());
    }

}
