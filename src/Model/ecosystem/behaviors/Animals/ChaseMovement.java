package Model.ecosystem.behaviors.Animals;

import Model.ecosystem.core.Environment;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.animals.Animal;
import Model.ecosystem.core.Position;
import Model.ecosystem.interfaces.EdibleByHerbivore;

import java.util.List;


public class ChaseMovement implements MovementStrategy
{

    @Override
    public boolean move(Animal animal, Environment env)
    {
        // Use environment to find the closest edible entity
        List<AbstractEntity> entities = env.getNearByEntities(animal.getM_position());
        for (AbstractEntity entity : entities)
        {
            if(entity instanceof EdibleByHerbivore)
            {
                Position chasePos = moveOneTileTowards(animal,entity,env); //if prey is found calculate the coordinates to move 1 step towards it
                if(chasePos != null)
                    animal.setM_position(chasePos);
                return true;
            }
        }

        RandomMovement randomMovement = new RandomMovement();
        randomMovement.move(animal,env);

        return randomMovement.move(animal,env);
    }

    //Helper Methods
    private Position moveOneTileTowards(Animal chaser, AbstractEntity target, Environment env)
    {
        Position chaserPos = chaser.getM_position();
        Position targetPos = target.getM_position();

        int newRow = chaserPos.getRow();
        int newCol = chaserPos.getCol();

        // Target is above chaser  move up
        if (targetPos.getRow() < chaserPos.getRow())
        {
            newRow--;
        }
        // Target is below chaser move down
        else if (targetPos.getRow() > chaserPos.getRow())
        {
            newRow++;
        }

        // Target is left of chaser move left
        if (targetPos.getCol() < chaserPos.getCol())
        {
            newCol--;
        }
        // Target is right of chaser move right
        else if (targetPos.getCol() > chaserPos.getCol())
        {
            newCol++;
        }

        Position chasePos = new Position(newRow, newCol);

        if (env.isPositionFree(chasePos))
        {
            return chasePos;
        }

        return null;
    }
}


