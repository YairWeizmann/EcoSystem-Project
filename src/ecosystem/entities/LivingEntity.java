package ecosystem.entities;
import ecosystem.core.Environment;
import ecosystem.core.Position;
import ecosystem.interfaces.Actable;

@SuppressWarnings("all")

public class LivingEntity extends AbstractEntity implements Actable
{
    // ===================== Fields =====================
    double m_energy;
    double m_maxEnergy;
    double m_age = 0f;

    // ===================== Constructors =====================

    public LivingEntity(Position position,char symbol , boolean is_alive , double energy , double max_energy , double age)
    {
        super(position,symbol,is_alive);
        this.m_energy = energy;
        this.m_maxEnergy = max_energy;
        this.m_age = age;
    } // Parameters Constructor

    //Default Living Entity
    public LivingEntity(Position position , char symbol , boolean is_alive)
    {
        super(position,symbol,is_alive);
    }

    //No Parameter Living Entity
    public LivingEntity()
    {
      super();
    }


    // ===================== Methods =====================
    //InterFaces Methods
    //Actable
    @Override
    public void act(Environment env)
    {
       m_age +=1; // Up The Age By 1
       m_energy -= 2; // Minus 2 Energy

       if(m_energy <= 0)
           this.setIs_alive(false);
    }
}
