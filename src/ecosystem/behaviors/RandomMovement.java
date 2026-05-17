package ecosystem.behaviors;

import ecosystem.core.Environment;
import ecosystem.core.Position;
import ecosystem.entities.animals.Animal;
import ecosystem.entities.animals.Rabbit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomMovement implements MovementStrategy
{

    @Override
    public boolean move(Animal animal, Environment env)
    {
        List<Position> freePositions = env.getFreeNearbyPositions(animal.getM_position());

        if (freePositions.isEmpty())
        {
            System.out.println("Not Found Free Positions to Go To");
            return false;
        }


        Random randomPosition = new Random(); // Randomize Generator
        int randomIndex = randomPosition.nextInt(freePositions.size()); //Chooses Random Position from free Positions


        Position newRandomPosition = freePositions.get(randomIndex);
        animal.setM_position(newRandomPosition);
        return true;

    }
}