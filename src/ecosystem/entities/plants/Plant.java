package ecosystem.entities.plants;

import ecosystem.interfaces.Consumable;
import ecosystem.interfaces.EdibleByHerbivore;

public class Plant implements Consumable , EdibleByHerbivore
{

    //InterFace Methods
    //Consumable
    @Override

    public double getNutritionValue()
    {
        System.out.println("Get Energy / Stamina");
        return 0.0f;
    }
    @Override
    public void onConsumed()
    {
        System.out.println("Is Alive = false , add Health , Etc");
        return;
    }
}
