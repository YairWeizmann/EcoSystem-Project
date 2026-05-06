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
    private List<AbstractEntity> m_entities = new ArrayList<>();

    // ============ Constructors ============
    public Environment(List<AbstractEntity> entities)
    {
        this.m_entities = entities;
    }

    public Environment()
    {

    }

    // ============ Methods ============
    public boolean isPositionFree(Position pos)
    {

        if (pos.getRow() < 0 || pos.getRow() >= rows ||
                pos.getCol() < 0 || pos.getCol() >= cols) //checks if outside the Map
        {
            return false;
        }

        // occupied by any entity
        for (AbstractEntity entity : m_entities)
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

        m_entities.add(entity);
        return true;
    }

    public boolean removeEntity(AbstractEntity entity)
    {
        if(entity == null)
            return false;

        if(!m_entities.contains(entity))
            return false;

        if(!entity.getIs_alive())
        {
            m_entities.remove(entity);
            return true;
        }
         return false;

    }

    public void removeDeadEntities()
    {
        m_entities.removeIf(entity -> !entity.getIs_alive());
    }

    //Map Related
    public void printMap()
    {
        char[][] map = new char[rows][cols];
        int rowX;
        int colY;

        // Fill empty cells
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                map[row][col] = '.';
            }
        }

        for(AbstractEntity entity : m_entities)
        {
            rowX = entity.getM_position().getRow();
            colY = entity.getM_position().getCol();

            if (rowX >= 0 && rowX < rows && colY >= 0 && colY < cols)
            {
                map[rowX][colY] = entity.getM_symbol();
            }        }

        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                System.out.print(map[row][col] + " ");
            }
            System.out.println();
        } //Print the Map


    }

    // ============ Getters / Setters ============


    public List<AbstractEntity> getM_entities()
    {
        return m_entities;
    }

    public boolean setM_entities(List<AbstractEntity> m_entities)
    {
        if(m_entities == null)
            return false;

        this.m_entities = m_entities;
        return true;
    }
}
