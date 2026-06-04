package Factories;

import Model.ecosystem.core.Position;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.animals.Deer;
import Model.ecosystem.entities.animals.Lion;
import Model.ecosystem.entities.animals.Rabbit;
import Model.ecosystem.entities.plants.Flower;
import Model.ecosystem.entities.plants.OakTree;
import Model.ecosystem.entities.resources.Rock;
import Model.ecosystem.entities.resources.Water;
import Model.ecosystem.interfaces.EcosystemCommand;

import java.util.concurrent.BlockingQueue;

public class EntityFactory
{
    private BlockingQueue<EcosystemCommand> m_commandQueue;

    public EntityFactory(BlockingQueue<EcosystemCommand> commandQueue)
    {
        this.m_commandQueue = commandQueue;
    }

    public AbstractEntity createEntity(AbstractEntity.EntityType entityType, Position position, int energy)
    {
        switch(entityType)
        {
            case Deer:
                return new Deer(position, m_commandQueue);

            case Rabbit:
                return new Rabbit(position, m_commandQueue);

            case Lion:
                return new Lion(position, m_commandQueue);

            case OakTree:
                return new OakTree(position,m_commandQueue);

            case Flower:
                return new Flower(position,m_commandQueue);

            case Water:
                return new Water(position);

            case Rock:
                return new Rock(position);

            default:
                return null;
        }
    }
}
