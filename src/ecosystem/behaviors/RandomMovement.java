package ecosystem.behaviors;

import ecosystem.core.Environment;
import ecosystem.entities.animals.Animal;

public class RandomMovement implements MovementStrategy {

    @Override
    public boolean move(Animal animal, Environment env) {

        // TODO: Implement random movement logic
        // 1. Get current position of the animal
        // 2. Ask Environment for all valid empty adjacent positions
        // 3. Pick one position randomly
        // 4. Update the animal's position to the new one

        return true;
    }
}