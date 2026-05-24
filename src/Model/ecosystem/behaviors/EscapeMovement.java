package Model.ecosystem.behaviors;

import Model.ecosystem.core.Environment;
import Model.ecosystem.core.Position;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.animals.Animal;
import Model.ecosystem.interfaces.EdibleByCarnivore;

import java.util.List;

public class  EscapeMovement implements MovementStrategy {

    @Override
    public boolean move(Animal animal, Environment env)
    {
        List<AbstractEntity> nearByEntites = env.getNearByEntities(animal.getM_position());
        for(AbstractEntity entity : nearByEntites)
        {
            if (entity == animal)
                continue;            // skip self entity

            if(entity instanceof EdibleByCarnivore) //If predator is found, calculate the coordinates to move 1 step away from it
            {
                Position runPos = runAwayOneTile(animal,entity,env);
                if(runPos != null)
                   animal.setM_position(runPos);
                return true;
            }
        }
        //Falls Back to Random Movement
        RandomMovement randomMovement = new RandomMovement();
        return randomMovement.move(animal,env);
    }

    //Helper Methods
    private Position runAwayOneTile(Animal animal ,AbstractEntity predator, Environment env)
    {
        Position animalPos = animal.getM_position();
        Position predatorPos = predator.getM_position();

        int newRow = animalPos.getRow();
        int newCol = animalPos.getCol();

        // Predator is above animal -> move down
        if (predatorPos.getRow() < animalPos.getRow())
        {
            newRow++;
        }
        // Predator is below animal -> move up
        else if (predatorPos.getRow() > animalPos.getRow())
        {
            newRow--;
        }

        // Predator is left of animal -> move right
        if (predatorPos.getCol() < animalPos.getCol())
        {
            newCol++;
        }
        // Predator is right of animal -> move left
        else if (predatorPos.getCol() > animalPos.getCol())
        {
            newCol--;
        }

        Position escapePos = new Position(newRow, newCol);

        if (env.isPositionFree(escapePos))
        {
            return escapePos;
        }

        return null;
    }
}