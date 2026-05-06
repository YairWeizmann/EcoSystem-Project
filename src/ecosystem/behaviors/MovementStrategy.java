package ecosystem.behaviors;

import ecosystem.core.Environment;
import ecosystem.entities.animals.Animal;

public interface MovementStrategy
{
    // Defines the movement contract: accepts the animal that moves and the environment

    boolean move(Animal animal, Environment env);

}
