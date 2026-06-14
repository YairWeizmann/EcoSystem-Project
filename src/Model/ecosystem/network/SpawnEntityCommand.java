package Model.ecosystem.network;

import Factories.EntityFactory;
import Model.ecosystem.core.Environment;
import Model.ecosystem.core.Position;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.animals.Animal;
import Model.ecosystem.entities.plants.Plant;

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
    public boolean execute(Environment environment, EntityFactory entityFactory)
    {
        if (environment == null || entityFactory == null)
        {
            System.out.println("Cannot execute spawn command: missing environment or entity factory.");
            return false;
        }

        // Protocol uses X,Y. Position uses row,col.
        Position position = new Position(m_y, m_x);
        AbstractEntity newEntity = entityFactory.createEntity(m_entityType, position, m_energy);

        if (newEntity == null)
        {
            System.out.println("Could not create received entity: " + m_entityType);
            return false;
        }

        boolean added = environment.addEntity(newEntity);

        if (!added)
        {
            System.out.println("Received entity was not added to the environment.");
            return false;
        }

        startEntityThread(newEntity);
        System.out.println("Received entity added to environment: " + m_entityType);
        return true;
    }

    @Override
    public void printCommandInfo()
    {
        System.out.println("Parsed network command: SPAWN " +
                m_entityType + " energy=" + m_energy +
                " x=" + m_x + " y=" + m_y);
    }

    private void startEntityThread(AbstractEntity entity)
    {
        if (!(entity instanceof Runnable))
            return;

        if (entity instanceof Animal)
        {
            ((Animal) entity).startThread();
        }
        else if (entity instanceof Plant)
        {
            ((Plant) entity).startThread();
        }

        Thread entityThread = new Thread((Runnable) entity);
        entityThread.setName("Received-" + entity.getM_entityType() + "-Thread");
        entityThread.start();
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
