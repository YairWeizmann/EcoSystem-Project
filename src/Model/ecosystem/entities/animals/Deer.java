package Model.ecosystem.entities.animals;
import Model.ecosystem.behaviors.EscapeMovement;
import Model.ecosystem.behaviors.HerbivoreBehavior;
import Model.ecosystem.core.Position;
import Model.ecosystem.interfaces.EdibleByHerbivore;

public class Deer extends Animal implements EdibleByHerbivore
{
    // ============ Constructors ============
    public Deer(Position position)
    {
        super(position, 'D', true,EntityType.Deer ,70, 70, 0,new HerbivoreBehavior(),
                new EscapeMovement());
    }

}
