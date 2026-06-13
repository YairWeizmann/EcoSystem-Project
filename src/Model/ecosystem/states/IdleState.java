package Model.ecosystem.states;

import Model.ecosystem.core.Environment;
import Model.ecosystem.entities.LivingEntity;
import Model.ecosystem.entities.animals.Animal;

public class IdleState implements EntityState
{
    @Override
    public void doAction(LivingEntity entity)
    {
        Animal currentAnimal = (Animal) entity;
        Environment currentEnv = currentAnimal.getM_currentEnv();

        currentAnimal.setM_energy(currentAnimal.getM_energy() - 1);


        if(currentAnimal.getM_energy() < currentAnimal.getM_maxEnergy() * 0.3f)
        {
            currentAnimal.setM_currentState(new HungryState());
            return;
        }

        if (currentAnimal.isInCorner(currentEnv))
        {
            currentAnimal.setM_currentState(new SleepingState());
            return;
        }


        // random move or stay
        if (Math.random() < 0.5)
        {
            currentAnimal.move(currentEnv);
        }

    }

}
