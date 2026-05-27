package Model.ecosystem.core;

import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.interfaces.Actable;

import java.util.ArrayList;
import java.util.List;
import View.Stats.StatsListener;


public class SimulationEngine
{
    // ============ Fields ============
    private Environment m_environment;
    private SimulationStats m_simulationStats;

    private List<StatsListener> m_statsListeners;

    // ============ Constructors ============
    public SimulationEngine(Environment environment)
    {
       InitComponents(environment);
    }

    // ============ Methods ============
    public void tick()
    {
      List<AbstractEntity> copyEntities = new ArrayList<>(m_environment.getM_entities());

      for(AbstractEntity entity : copyEntities)
      {
          if(entity instanceof Actable)
              ((Actable) entity).act(m_environment);

      }

      this.m_environment.removeDeadEntities();

      this.m_simulationStats.update(m_environment);

        System.out.println("New Map");
      this.m_environment.printMap();

      notifyStatsChanged();

    }

    // ============ Helper Methods ============
    private void InitComponents(Environment environment)
    {
        this.m_environment = environment;
        this.m_simulationStats = new SimulationStats();
        this.m_statsListeners = new ArrayList<>();
    }

    // ============ Observer Methods ============
    public void addStatsListener(StatsListener listener)
    {
        if(listener != null)
            m_statsListeners.add(listener);
    }

    private void notifyStatsChanged()
    {
        for(StatsListener listener : m_statsListeners)
        {
            listener.onStatsChanged();
        }
    }

    // ============ Getters / Setters ============

    public SimulationStats getSimulationStats()
    {
        return this.m_simulationStats;
    }



}
