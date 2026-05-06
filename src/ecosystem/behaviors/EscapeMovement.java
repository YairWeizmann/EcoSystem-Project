package ecosystem.behaviors;

import ecosystem.core.Environment;
import ecosystem.entities.animals.Animal;

public class EscapeMovement implements MovementStrategy {

    @Override
    public boolean move(Animal animal, Environment env) {

        // TODO: Implement escape movement logic
        // 1. Use environment to find the closest predator
        // 2. If predator is found, calculate the coordinates to move 1 step away from it
        // 3. If no predator is found, fallback to RandomMovement
        // 4. Update the animal's position

        return true;
    }
}