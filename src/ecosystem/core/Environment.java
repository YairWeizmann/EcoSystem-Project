package ecosystem.core;

import ecosystem.entities.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

public class Environment
{
    // ============ Fields ============
    //Map Variables
    private int rows = 10;
    private int cols = 10;

    //Entities
    private List<AbstractEntity> entities = new ArrayList<>();

    // ============ Constructors ============


    // ============ Methods ============
    public boolean isPositionFree(Position pos)
    {

        if (pos.getRow() < 0 || pos.getRow() >= rows ||
                pos.getCol() < 0 || pos.getCol() >= cols) //checks if outside the Map
        {
            return false;
        }

        // occupied by any entity
        for (AbstractEntity entity : entities)
        {
            if (entity != null &&
                    entity.getIs_alive() &&
                    entity.getM_position().equals(pos))
            {
                return false;
            }
        }
        return true;
    }

    public boolean addEntity(AbstractEntity entity)
    {
        if(entity == null)
            return false;

        if(!isPositionFree(entity.getM_position()))
            return false;

        entities.add(entity);
        return true;
    }

    public boolean removeEntity(AbstractEntity entity)
    {
        if(entity == null)
            return false;

        if(!entities.contains(entity))
            return false;

        entities.remove(entity);
        return true;
    }

}
