package ecosystem.behaviors;

import ecosystem.entities.AbstractEntity;
import ecosystem.entities.animals.Animal;
import java.util.List;

@SuppressWarnings("all")
public class CarnivoreBehavior extends FeedingBehavior
{
    // ============ Interface Methods ============

    @Override
    public boolean eat(Animal animal, List<AbstractEntity> nearbyEntities)
    {
        // Carnivore eating logic will be implemented here later
        return false;
    }
}