package Model.ecosystem.states;

import Model.ecosystem.core.Environment;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.LivingEntity;
import Model.ecosystem.entities.animals.Animal;

import java.util.List;

public class HungryState implements EntityState
{
    @Override
    public void doAction(LivingEntity entity)
    {
        Animal currentAnimal = (Animal) entity;
        Environment currentEnv = currentAnimal.getM_currentEnv();

        boolean ate = false;

        List<AbstractEntity> nearby = currentAnimal.sense(currentEnv);

        if (currentAnimal.getM_eatBehavior() != null && nearby != null && !nearby.isEmpty())
        {
            ate = currentAnimal.getM_eatBehavior().eat(currentAnimal, nearby);
        }

        if (!ate)
        {
            currentAnimal.move(currentEnv);
        }

        currentAnimal.setM_energy(currentAnimal.getM_energy() - 5);

        if (currentAnimal.getM_energy() > currentAnimal.getM_maxEnergy() * 0.8f)
        {
            currentAnimal.setM_currentState(new IdleState());
        }
    }
}

