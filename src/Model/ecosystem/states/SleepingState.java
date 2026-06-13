package Model.ecosystem.states;

import Model.ecosystem.entities.LivingEntity;
import Model.ecosystem.entities.animals.Animal;

public class SleepingState implements EntityState
{
    private final int SLEEP_DURATION = 5;


    private int m_ticksLeft;

    public SleepingState()
    {
        this.m_ticksLeft = SLEEP_DURATION;
    }


    @Override
    public void doAction(LivingEntity entity)
    {
        Animal currentAnimal = (Animal) entity;

        double newEnergy = currentAnimal.getM_energy() + 5f;

        if (newEnergy > currentAnimal.getM_maxEnergy())
            newEnergy = currentAnimal.getM_maxEnergy();

        currentAnimal.setM_energy(newEnergy);

        m_ticksLeft--;

        if (m_ticksLeft <= 0)
        {
            currentAnimal.setM_currentState(new IdleState());
        }


    }

}
