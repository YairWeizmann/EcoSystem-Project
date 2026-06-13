package Model.ecosystem.states;

import Model.ecosystem.entities.LivingEntity;

public interface EntityState
{
    void doAction(LivingEntity entity);
}
