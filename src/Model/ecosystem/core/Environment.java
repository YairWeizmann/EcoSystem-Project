package Model.ecosystem.core;

import Model.ecosystem.entities.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

public class Environment
{
    // ============ FIELDS ============

    // Map Variables
    private final int rows = 10;
    private final int cols = 14;

    // Entities
    private List<AbstractEntity> m_Spawnedentities = new ArrayList<>();


    // ============ CONSTRUCTORS ============

    public Environment()
    {

    }

    public Environment(List<AbstractEntity> entities)
    {
        if(entities != null)
            this.m_Spawnedentities = entities;
    }


    // ============ POSITION / ENTITY METHODS ============

    public boolean isPositionFree(Position pos)
    {
        if(pos == null)
            return false;

        if(pos.getRow() < 0 || pos.getRow() >= rows ||
                pos.getCol() < 0 || pos.getCol() >= cols)
        {
            return false;
        }

        for(AbstractEntity entity : m_Spawnedentities)
        {
            if(entity != null &&
                    entity.getIs_alive() &&
                    entity.getM_position().equals(pos))
            {
                return false;
            }
        }

        return true;
    }

    public synchronized boolean addEntity(AbstractEntity entity)
    {
        if(entity == null)
        {
            System.out.println("Entity Not Found");
            return false;
        }

        if(!isPositionFree(entity.getM_position()))
        {
            System.out.println("Position Not Free");
            return false;
        }

        m_Spawnedentities.add(entity);
        return true;
    }

    public boolean removeEntity(AbstractEntity entity)
    {
        if(entity == null)
            return false;

        if(!m_Spawnedentities.contains(entity))
            return false;

        m_Spawnedentities.remove(entity);
        return true;
    }

    public synchronized void removeDeadEntities()
    {
        m_Spawnedentities.removeIf(entity -> entity != null && !entity.getIs_alive());
    }

    public synchronized void resetEntities()
        {
            this.m_Spawnedentities.clear();
        }

    // ============ MAP METHODS ============

    public void printMap()
    {
        char[][] map = new char[rows][cols];

        for(int row = 0; row < rows; row++)
        {
            for(int col = 0; col < cols; col++)
            {
                map[row][col] = '.';
            }
        }

        for(AbstractEntity entity : m_Spawnedentities)
        {
            if(entity == null || entity.getM_position() == null)
                continue;

            int row = entity.getM_position().getRow();
            int col = entity.getM_position().getCol();

            if(row >= 0 && row < rows && col >= 0 && col < cols)
            {
                map[row][col] = entity.getM_symbol();
            }
        }

        for(int row = 0; row < rows; row++)
        {
            for(int col = 0; col < cols; col++)
            {
                System.out.print(map[row][col] + " ");
            }

            System.out.println();
        }
    }


    // ============ NEARBY METHODS ============

    public List<AbstractEntity> getNearByEntities(Position pos)
    {
        List<AbstractEntity> nearByEntities = new ArrayList<>();

        if(pos == null)
            return nearByEntities;

        for(AbstractEntity entity : m_Spawnedentities)
        {
            if(entity == null || entity.getM_position() == null)
                continue;

            int distance =
                    Math.abs(pos.getRow() - entity.getM_position().getRow()) +
                            Math.abs(pos.getCol() - entity.getM_position().getCol());

            if(distance <= 2)
                nearByEntities.add(entity);
        }

        return nearByEntities;
    }

    public Position getFreeNearbyPos(Position pos , int distance)
    {
        List<Position> freePositions = getFreeNearbyPositions(pos , distance);

        if(freePositions.isEmpty())
            return null;

        return freePositions.get(0);
    }

    public List<Position> getFreeNearbyPositions(Position pos , int distance)
    {
        List<Position> freePositions = new ArrayList<>();

        if(pos == null)
            return freePositions;

        int currentRow = pos.getRow();
        int currentCol = pos.getCol();

        Position posUp = new Position(currentRow - distance, currentCol);
        Position posDown = new Position(currentRow + distance, currentCol);
        Position posLeft = new Position(currentRow, currentCol - distance);
        Position posRight = new Position(currentRow, currentCol + distance);

        List<Position> positions = new ArrayList<>();

        positions.add(posUp);
        positions.add(posDown);
        positions.add(posLeft);
        positions.add(posRight);

        for(Position position : positions)
        {
            if(isPositionFree(position))
                freePositions.add(position);
        }

        return freePositions;
    }


    // ============ GETTERS / SETTERS ============

    public synchronized List<AbstractEntity> getM_entities()
    {
        return new ArrayList<>(this.m_Spawnedentities);
    }

    public boolean setM_entities(List<AbstractEntity> entities)
    {
        if(entities == null)
            return false;

        this.m_Spawnedentities = entities;
        return true;
    }

    public int getRows()
    {
        return rows;
    }

    public int getCols()
    {
        return cols;
    }

    public AbstractEntity getEntityAt(int row , int col)
    {
        for (AbstractEntity entity : m_Spawnedentities)
        {
            if(entity.getM_position().getRow() == row && entity.getM_position().getCol() == col)
                return entity;
        }
        return null;
    }
}