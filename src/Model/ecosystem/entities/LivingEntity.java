package Model.ecosystem.entities;
import Model.ecosystem.core.Environment;
import Model.ecosystem.core.Position;
import Model.ecosystem.interfaces.Actable;

@SuppressWarnings("all")

public class LivingEntity extends AbstractEntity implements Actable
{
    // ===================== Fields =====================
    private double m_energy;
    private double m_maxEnergy;
    private double m_age = 0f;

    // ===================== Constructors =====================

    public LivingEntity(Position position,char symbol , boolean is_alive ,EntityType entityType , double energy , double max_energy , double age)
    {
        super(position,symbol,is_alive,entityType);
        this.m_energy = energy;
        this.m_maxEnergy = max_energy;
        this.m_age = age;
    } // Parameters Constructor

    //Default Living Entity
    public LivingEntity(Position position , char symbol , boolean is_alive , EntityType entityType)
    {
        super(position,symbol,is_alive,entityType);
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
        m_energy -= 2; // down the energy by 2

        if(m_energy <= 0)
           this.setIs_alive(false);
    }

    // ===================== Getters & Setters =====================

    public double getM_energy() {
        return m_energy;
    }

    public boolean setM_energy(double m_energy)
    {
        if(m_energy < 0)
            return false;

        this.m_energy = m_energy;
        return true;
    }

    public double getM_maxEnergy() {
        return m_maxEnergy;
    }

    public boolean setM_maxEnergy(double m_maxEnergy)
    {
        if(m_maxEnergy < 0)
            return false;

        this.m_maxEnergy = m_maxEnergy;
        return true;
    }

    public double getM_age()
    {
        return m_age;
    }

    public boolean setM_age(double m_age)
    {
        if(m_age < 0)
            return false;

        this.m_age = m_age;
        return true;
    }
}
