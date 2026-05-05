package ecosystem.entities.plants;

import ecosystem.core.Environment;
import ecosystem.core.Position;

@SuppressWarnings("all")
public class OakTree extends Plant
{

    // ===================== FIELDS =====================



    // ===================== Constructors =====================
    public OakTree(Position position, char symbol, boolean is_alive, double energy, double max_energy, double age, double growthRate, double reproductionChance)
    {
        super(position, symbol, is_alive, energy = 80f, max_energy = 120, age, growthRate = 2, reproductionChance = 0.05f);
    }

    // ===================== Interfaces Methods =====================
    //Reproducible
    @Override
    public boolean reproduce(Environment env)
    {
        //New Slot in Distance 1 from the current Oak Tree

        return true;
    }



}
