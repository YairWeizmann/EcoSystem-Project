package ecosystem.core;

import ecosystem.behaviors.CarnivoreBehavior;
import ecosystem.entities.AbstractEntity;
import ecosystem.entities.LivingEntity;

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

    // ============ Position / Entities Methods ============
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
        if(entity == null){
            System.out.println("Entity Not Found");
            return false;
        }

        if(!isPositionFree(entity.getM_position()))
        {
            System.out.println("Position Not free");
            return false;
        }

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

            System.out.println("Spawned " + entity.getClass().getName());

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

    public List<AbstractEntity> getNearByEntities(Position pos)
    {
       List<AbstractEntity> nearByEntities = new ArrayList<>();
       for(AbstractEntity entity : m_entities)
       {
           int distance = Math.abs(pos.getRow() - entity.getM_position().getRow()) +
                           Math.abs(pos.getCol() - entity.getM_position().getCol()); // Manhatan distance

           if(distance <= 2)
               nearByEntities.add(entity);
       }

       return nearByEntities;
    }


    public Position getFreeNearbyPos(Position pos) //Checks for nearBy Free Positions near an entity
    {
        //Col & Row
        int currentRow = pos.getRow();
        int currentCol = pos.getCol();

        Position posUp = new Position(currentRow - 1 , currentCol); // posUp from Entity
        Position posDown = new Position(currentRow + 1 , currentCol); //posDown from Entity
        Position posLeft = new Position(currentRow,currentCol - 1); //posLeft from Entity
        Position posRight = new Position(currentRow , currentCol + 1); //posRight from Entity

        List<Position> positions = new ArrayList<>();
        positions.add(posUp);
        positions.add(posDown);
        positions.add(posLeft);
        positions.add(posRight);

        for (Position position : positions)
        {
            if (isPositionFree(position))
                return position;
        }

        return null;
    }

    public List<Position> getFreeNearbyPositions(Position pos) //Checks for nearBy Free Positions near an entity
    {
        //Col & Row
        int currentRow = pos.getRow();
        int currentCol = pos.getCol();

        Position posUp = new Position(currentRow - 1 , currentCol); // posUp from Entity
        Position posDown = new Position(currentRow + 1 , currentCol); //posDown from Entity
        Position posLeft = new Position(currentRow,currentCol - 1); //posLeft from Entity
        Position posRight = new Position(currentRow , currentCol + 1); //posRight from Entity

        List<Position> positions = new ArrayList<>();
        positions.add(posUp);
        positions.add(posDown);
        positions.add(posLeft);
        positions.add(posRight);

        List<Position> freePositions = new ArrayList<>();

        for (Position position : positions)
        {
            if (isPositionFree(position))
                freePositions.add(position);
        }

        return freePositions;
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
