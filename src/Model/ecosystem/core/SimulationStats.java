package Model.ecosystem.core;

public class SimulationStats
{
    // ===================== FIELDS =====================
    private int m_ticks;
    private int m_totalEntities;
    private double m_totalEnergy;

    // ===================== METHODS =====================
    public void update(Environment environment)
    {
        m_totalEntities = environment.getM_entities().size();
        m_totalEnergy = 0;

        for(var entity : environment.getM_entities())
        {
            if(entity instanceof Model.ecosystem.entities.LivingEntity)
            {
                Model.ecosystem.entities.LivingEntity livingEntity =
                        (Model.ecosystem.entities.LivingEntity) entity;

                m_totalEnergy += livingEntity.getM_energy();
            }
        }

        increaseTick();
    }

    public void increaseTick()
    {
        m_ticks++;
    }

    public void reset()
    {
        m_ticks = 0;
        m_totalEntities = 0;
        m_totalEnergy = 0;
    }

    // ===================== GETTERS =====================
    public int getM_ticks()
    {
        return m_ticks;
    }

    public int getM_totalEntities()
    {
        return m_totalEntities;
    }

    public double getM_totalEnergy()
    {
        return m_totalEnergy;
    }

}
