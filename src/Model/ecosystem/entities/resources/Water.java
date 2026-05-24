package Model.ecosystem.entities.resources;

import Model.ecosystem.core.Environment;
import Model.ecosystem.core.Position;
import Model.ecosystem.interfaces.Consumable;

public class Water extends Resource implements Consumable
{
    // ===================== Constructors =====================
     public Water(Position pos)
     {
         super(pos,'W', EntityType.Water);

     }

    // ===================== InterFaces Methods =====================
    @Override
    public double getNutritionValue()
    {
         return 100f;
    }

    @Override
    public void onConsumed()
    {
         return;
    }


}
