package ecosystem.entities.plants;
import ecosystem.core.Environment;
import ecosystem.core.Position;
import ecosystem.entities.LivingEntity;
import ecosystem.interfaces.Consumable;
import ecosystem.interfaces.EdibleByHerbivore;
import ecosystem.interfaces.Reproducible;

@SuppressWarnings("all")
public abstract class Plant extends LivingEntity implements Consumable , Reproducible, EdibleByHerbivore
{

    // ===================== FIELDS =====================
    private double m_growthRate;
    private double m_reproductionChance;

    // ===================== Constructors =====================
    public Plant(Position position, char symbol , boolean is_alive , double energy , double max_energy , double age
            , double growthRate , double reproductionChance)
    {
      super(position,symbol,is_alive,energy,max_energy,age);
      setM_growthRate(growthRate);
      setM_reproductionChance(reproductionChance);
    }

    // ===================== InterFace Methods =====================
    //Consumable
    @Override

    public double getNutritionValue() //How much energy Does that plant give
    {
        return this.getM_energy();
    }


    @Override
    public void onConsumed() //Someone Consumed Planet
    {
        this.setIs_alive(false);
    }
    //Actable
    @Override
    public void act(Environment env) //How the Plant Acts
    {
        this.setM_age(this.getM_age() + 1);

        double newEnergy = this.getM_energy() + this.m_growthRate; // Local Variable For The new Energy

        if(newEnergy > this.getM_maxEnergy()) // Checks if The Improved Energy isnt above the max Energy
        {
            newEnergy = this.getM_maxEnergy();
        }

        this.setM_energy(newEnergy);
    }




    // ===================== Getters & Setters Methods =====================


    public double getM_growthRate()
    {
        return m_growthRate;
    }

    public boolean setM_growthRate(double m_growthRate)
    {
        if(m_growthRate < 0)
            return false;

        this.m_growthRate = m_growthRate;
        return true;
    }

    public double getM_reproductionChance()
    {
        return m_reproductionChance;
    }

    public boolean setM_reproductionChance(double m_reproductionChance)
    {
        if(m_reproductionChance < 0)
            return false;

        this.m_reproductionChance = m_reproductionChance;
        return true;
    }
}
