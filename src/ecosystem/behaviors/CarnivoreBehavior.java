package ecosystem.behaviors;

import ecosystem.entities.AbstractEntity;
import ecosystem.entities.animals.Animal;
import ecosystem.interfaces.Consumable;
import ecosystem.interfaces.EdibleByCarnivore;

import java.util.List;

public class CarnivoreBehavior implements FeedingBehavior {

    @Override
    public boolean eat(Animal eater, List<AbstractEntity> nearby) {

        for (AbstractEntity entity : nearby) // Loop through all nearby entities
        {

            // Check if the entity is alive AND has the carnivore-edible tag
            if (entity.getIs_alive() && entity instanceof EdibleByCarnivore) {

                // Cast the entity to Consumable so we can get its nutrition
                Consumable prey = (Consumable) entity;

                // Calculate new energy (do not exceed max energy)
                double newEnergy = eater.getM_energy() + prey.getNutritionValue();
                if (newEnergy > eater.getM_energy()) {
                    newEnergy = eater.getM_energy();
                }

                eater.setM_energy(newEnergy); // Update eater's energy

                prey.onConsumed(); // Trigger the prey's death/consumed logic

                return true;  // Return true because we successfully ate something
            }
        }

        return false; // Return false if nothing edible was found
    }
}