package Model.ecosystem.behaviors.Animals;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.animals.Animal;
import java.util.List;

public interface FeedingBehavior
{
    // Defines the eating contract: accepts the eating animal and a list of nearby entities

    boolean eat(Animal eater, List<AbstractEntity> nearby);
}
