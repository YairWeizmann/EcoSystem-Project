package Model.ecosystem.entities.animals;

import Model.ecosystem.behaviors.Animals.FeedingBehavior;
import Model.ecosystem.behaviors.Animals.MovementStrategy;
import Model.ecosystem.core.Environment;
import Model.ecosystem.core.Position;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.LivingEntity;
import Model.ecosystem.interfaces.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public abstract class Animal extends LivingEntity implements Movable , Eater ,
  EdibleByCarnivore, Sensory , Consumable
{

    // ===================== FIELDS =====================
    private FeedingBehavior m_eatBehavior;
    private MovementStrategy m_animalMove;
    private int m_visionRange;


    // ===================== Constructors =====================
    public Animal(Position position, char symbol , boolean is_alive,EntityType entityType, double energy , double maxEnergy , double age ,
                  FeedingBehavior eatBehavior, MovementStrategy animalMove)
    {
        super(position,symbol , is_alive , entityType , energy , maxEnergy , age);
        this.m_eatBehavior = eatBehavior;
        this.m_animalMove = animalMove;
        this.m_visionRange = 2;
    } //Animal Constructor



    // ===================== getters & setters =====================


    public FeedingBehavior getM_eatBehavior()
    {
        return m_eatBehavior;
    }

    public void setM_eatBehavior(FeedingBehavior m_eatBehavior)
    {
        this.m_eatBehavior = m_eatBehavior;
    }

    public MovementStrategy getM_animalMove()
    {
        return m_animalMove;
    }

    public void setM_animalMove(MovementStrategy m_animalMove) {
        this.m_animalMove = m_animalMove;
    }

    //Interface Methods
    //Moveable
    @Override
    public boolean move(Environment env) //Move
    {
        if (this.m_animalMove == null)
        {
            return false;
        }

        return this.m_animalMove.move(this, env);

    }

    //Eater
    @Override
    public boolean eat(Consumable target) //eat a Consumable
    {
      if(this.m_eatBehavior == null)
          return false;

        double newEnergy = this.getM_energy() + target.getNutritionValue();

        if (newEnergy > this.getM_maxEnergy())
            newEnergy = this.getM_maxEnergy();

        this.setM_energy(newEnergy);
        target.onConsumed();   // plant dies , water consumed , etc

        return true;
    }

    //Sensory
    @Override
    public List<AbstractEntity> sense(Environment env) // get all Entities in Distance // Later
    {
        if (env == null)
            return new ArrayList<>();   // import java.util.ArrayList;

        return env.getNearByEntities(this.getM_position());
    }

    //Consumable
    @Override
    public double getNutritionValue()
    {
      return 0.8f * this.getM_energy();
    }

    @Override
    public void onConsumed()
    {
        this.setIs_alive(false);
    }

    //Actable (Father)
    @Override
    public void act(Environment env )
    {
        super.act(env); //Father Acts (Removing Energy By 2 , age +1 , checks if Energy Below Zero)

        // check if is alive:
        if (!getIs_alive())
            return;

        List<AbstractEntity> nearby = sense(env);

        boolean ate = false;
        if (m_eatBehavior != null && nearby != null && !nearby.isEmpty())
        {
            ate = m_eatBehavior.eat(this, nearby);
        }
        if (!ate)  //Nothing edible around than move
           move(env);

    }
}
