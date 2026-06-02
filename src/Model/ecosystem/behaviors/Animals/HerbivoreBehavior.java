package Model.ecosystem.behaviors.Animals;

import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.animals.Animal;
import Model.ecosystem.entities.plants.Plant;
import Model.ecosystem.interfaces.Consumable;

import java.util.List;

public class HerbivoreBehavior implements FeedingBehavior {

    @Override
    public boolean eat(Animal eater, List<AbstractEntity> nearby) {

        for (AbstractEntity entity : nearby) // Loop through all nearby entities
        {
            // Skip the eater itself — no self-eating
            if (entity == eater)
                continue;

            // Check if the entity is alive AND has the herbivore-edible tag
            if (entity.getIs_alive() && entity instanceof Plant)
            {
                Consumable plant = (Consumable) entity;

                // Calculate new energy (do not exceed max energy)
                double newEnergy = eater.getM_energy() + plant.getNutritionValue();
                if (newEnergy > eater.getM_maxEnergy())
                {
                    newEnergy = eater.getM_maxEnergy();
                }

                eater.setM_energy(newEnergy); // Update eater's energy
                plant.onConsumed();           // Trigger the plant's consumed logic

                return true; // Successfully ate something
            }
        }

        return false; // Nothing edible found
    }

}