package ecosystem.entities.animals;

import ecosystem.behaviors.FeedingBehavior;
import ecosystem.behaviors.MovementStrategy;
import ecosystem.core.Environment;
import ecosystem.core.Position;
import ecosystem.entities.AbstractEntity;
import ecosystem.entities.LivingEntity;
import ecosystem.interfaces.*;

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
    public Animal(Position position, char symbol , boolean is_alive, double energy , double maxEnergy , double age ,
                  FeedingBehavior eatBehavior, MovementStrategy animalMove)
    {
        super(position,symbol , is_alive , energy , maxEnergy , age);
        this.m_eatBehavior = eatBehavior;
        this.m_animalMove = animalMove;
        this.m_visionRange = 2;
    } //Animal Constructor



    //Interface Methods
    //Moveable
    @Override
    public boolean move(Environment env) //Move
    {
        return true;
    }

    //Eater
    @Override
    public boolean eat(Consumable target) //eat a Consumable
    {
     System.out.println("Eating");
     return true;
    }

    //Sensory
    @Override
    public List<AbstractEntity> sense(Environment env) // get all Entities in Distance // Later
    {
      return null;
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
        super.act(env);//Father Acts (Removing Energy By 2 , age +1 , checks if Energy Below Zero)

        // check if is alive:
        if (!getIs_alive())
            return;



        sense(env); // Checks for NearBy Entities




    }



}
