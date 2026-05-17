package ecosystem.core;

import ecosystem.behaviors.MovementStrategy;
import ecosystem.entities.AbstractEntity;
import ecosystem.entities.animals.Animal;
import ecosystem.interfaces.Actable;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine
{
    // ============ Fields ============
    private Environment m_environment;


    // ============ Constructors ============
    public SimulationEngine(Environment environment)
    {
        this.m_environment = environment;
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
      System.out.println("New Map");
      this.m_environment.printMap();
    }



}
