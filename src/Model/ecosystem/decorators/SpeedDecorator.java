package Model.ecosystem.decorators;

import Model.ecosystem.core.Environment;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.LivingEntity;
import Model.ecosystem.interfaces.Actable;

public class SpeedDecorator extends EntityDecorator
{
    public SpeedDecorator(Actable decoratedEntity)
    {
        super(decoratedEntity);
    }

    @Override
    public void act(Environment env)
    {
        // first normal action
        Actable decoratedEntity = getDecoratedEntity();

        decoratedEntity.act(env);

        // second action only if still alive
        if (decoratedEntity instanceof LivingEntity)
        {
            LivingEntity livingEntity = (LivingEntity) decoratedEntity;

            if (livingEntity.getIs_alive())
            {
                decoratedEntity.act(env);
            }
        }
        else
        {
            decoratedEntity.act(env);
        }

        // sync decorator position with original entity
        AbstractEntity originalEntity = (AbstractEntity) decoratedEntity;
        this.setM_position(originalEntity.getM_position());

        // duration
        setM_ticksLeft(getM_ticksLeft() - 1);

        if (getM_ticksLeft() <= 0)
        {
            env.replaceEntity(this, originalEntity);
            System.out.println("Speed Finished");
        }
    }
}