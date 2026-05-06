package ecosystem.entities.animals;
import ecosystem.behaviors.EscapeMovement;
import ecosystem.behaviors.FeedingBehavior;
import ecosystem.behaviors.HerbivoreBehavior;
import ecosystem.core.Position;

public class Deer extends Animal
{
    // ============ Constructors ============
    public Deer(Position position)
    {
        super(position, 'D', true, 70, 100, 0,new HerbivoreBehavior(),
                new EscapeMovement());
    }


}
