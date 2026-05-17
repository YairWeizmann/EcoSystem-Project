package ecosystem.entities.resources;

import ecosystem.core.Position;
import ecosystem.interfaces.Consumable;

public class Water extends Resource implements Consumable
{
    // ===================== Constructors =====================
     public Water(Position pos)
     {
         super(pos,'W');

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
