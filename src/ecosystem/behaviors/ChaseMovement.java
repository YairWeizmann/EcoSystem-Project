package ecosystem.behaviors;

import ecosystem.core.Environment;
import ecosystem.entities.animals.Animal;

public class ChaseMovement implements MovementStrategy {

    @Override
    public boolean move(Animal animal, Environment env) {

        // TODO: Implement chase movement logic
        // 1. Use environment to find the closest edible entity
        // 2. If prey is found, calculate the coordinates to move 1 step towards it
        // 3. If no prey is found, fallback to RandomMovement
        // 4. Update the animal's position

        return true;
    }
}