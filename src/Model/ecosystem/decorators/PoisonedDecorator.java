package Model.ecosystem.decorators;

import Model.ecosystem.core.Environment;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.LivingEntity;
import Model.ecosystem.interfaces.Actable;

public class PoisonedDecorator extends EntityDecorator
{
    public PoisonedDecorator(Actable decoratedEntity)
    {
        super(decoratedEntity);
    }

    @Override
    public void act(Environment env)
    {
        Actable decoratedEntity = getDecoratedEntity();

        decoratedEntity.act(env);

        LivingEntity livingEntity = getWrappedLivingEntity();

        // Apply poison damage if the wrapped entity is living
        if (livingEntity != null)
        {
            livingEntity.setM_energy(livingEntity.getM_energy() - 5);

            System.out.println("Energy after poison: " + livingEntity.getM_energy());


            if (livingEntity.getM_energy() <= 0)
                livingEntity.setIs_alive(false);
        }
        // Keep the decorator in the same position as the original entity
        AbstractEntity originalEntity = (AbstractEntity) decoratedEntity;
        this.setM_position(originalEntity.getM_position());

        setM_ticksLeft(getM_ticksLeft() - 1);

        if (getM_ticksLeft() <= 0)
        {
            env.replaceEntity(this, originalEntity);
            System.out.println("Finished Poison");
        }
    }

    private LivingEntity getWrappedLivingEntity()
    {
        Actable decoratedEntity = getM_decoratedEntity();

        if (decoratedEntity instanceof LivingEntity)
            return (LivingEntity) decoratedEntity;

        if (decoratedEntity instanceof EntityDecorator)
        {
            Actable inner = ((EntityDecorator) decoratedEntity).getDecoratedEntity();


            if (inner instanceof LivingEntity)
                return (LivingEntity) inner;
        }

        return null;
    }
}
