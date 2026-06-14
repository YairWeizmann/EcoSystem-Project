package Factories;

import Model.ecosystem.core.Position;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.LivingEntity;
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
        AbstractEntity entity;

        switch(entityType)
        {
            case Deer:
                entity = new Deer(position, m_commandQueue);
                break;

            case Rabbit:
                entity = new Rabbit(position, m_commandQueue);
                break;

            case Lion:
                entity = new Lion(position, m_commandQueue);
                break;

            case OakTree:
                entity = new OakTree(position,m_commandQueue);
                break;

            case Flower:
                entity = new Flower(position,m_commandQueue);
                break;

            case Water:
                entity = new Water(position);
                break;

            case Rock:
                entity = new Rock(position);
                break;

            default:
                return null;
        }

        if(entity instanceof LivingEntity && energy >= 0)
        {
            ((LivingEntity) entity).setM_energy(energy);
        }

        return entity;
    }
}
