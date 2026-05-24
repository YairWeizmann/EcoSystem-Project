package Model.ecosystem.behaviors;

import Model.ecosystem.core.Environment;
import Model.ecosystem.entities.animals.Animal;

public interface MovementStrategy
{
    // Defines the movement contract: accepts the animal that moves and the environment

    boolean move(Animal animal, Environment env);

}
