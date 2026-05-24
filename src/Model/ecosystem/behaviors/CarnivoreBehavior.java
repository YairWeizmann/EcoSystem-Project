package Model.ecosystem.behaviors;

import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.animals.Animal;
import Model.ecosystem.interfaces.Consumable;
import Model.ecosystem.interfaces.EdibleByCarnivore;

import java.util.List;

public class CarnivoreBehavior implements FeedingBehavior {

    @Override
    public boolean eat(Animal eater, List<AbstractEntity> nearby) {

        for (AbstractEntity entity : nearby) // Loop through all nearby entities
        {
            // Skip the eater itself — no self-eating
            if (entity == eater)
                continue;

            // Check if the entity is alive AND has the carnivore-edible tag
            if (entity.getIs_alive() && entity instanceof EdibleByCarnivore)
            {
                Consumable prey = (Consumable) entity;

                // Calculate new energy (do not exceed max energy)
                double newEnergy = eater.getM_energy() + prey.getNutritionValue();
                if (newEnergy > eater.getM_maxEnergy())
                {
                    newEnergy = eater.getM_maxEnergy();
                }

                eater.setM_energy(newEnergy); // Update eater's energy
                prey.onConsumed();            // Trigger the prey's death/consumed logic

                return true; // Successfully ate something
            }
        }

        return false; // Nothing edible found
    }
}