package Model.ecosystem.behaviors.Animals;

import Model.ecosystem.core.Environment;
import Model.ecosystem.core.Position;
import Model.ecosystem.entities.animals.Animal;

import java.util.List;
import java.util.Random;

public class RandomMovement implements MovementStrategy
{

    @Override
    public boolean move(Animal animal, Environment env)
    {
        int tileMove = 1;
        List<Position> freePositions = env.getFreeNearbyPositions(animal.getM_position(),tileMove);

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