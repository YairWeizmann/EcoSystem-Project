package Model.ecosystem.network;

import Model.ecosystem.entities.AbstractEntity;

/**
 * Command that stores the data needed to spawn an entity received from the network.
 */
public class SpawnEntityCommand implements NetworkCommand
{
    private AbstractEntity.EntityType m_entityType;
    private int m_energy;
    private int m_x;
    private int m_y;

    public SpawnEntityCommand(AbstractEntity.EntityType entityType, int energy, int x, int y)
    {
        this.m_entityType = entityType;
        this.m_energy = energy;
        this.m_x = x;
        this.m_y = y;
    }

    @Override
    public void printCommandInfo()
    {
        System.out.println("Parsed network command: SPAWN " +
                m_entityType + " energy=" + m_energy +
                " x=" + m_x + " y=" + m_y);
    }

    public AbstractEntity.EntityType getM_entityType()
    {
        return m_entityType;
    }

    public int getM_energy()
    {
        return m_energy;
    }

    public int getM_x()
    {
        return m_x;
    }

    public int getM_y()
    {
        return m_y;
    }
}
