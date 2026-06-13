package Model.ecosystem.entities.animals;

import Model.ecosystem.behaviors.Animals.FeedingBehavior;
import Model.ecosystem.behaviors.Animals.MovementStrategy;
import Model.ecosystem.commands.ActCommand;
import Model.ecosystem.core.Environment;
import Model.ecosystem.core.Position;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.LivingEntity;
import Model.ecosystem.interfaces.*;
import Model.ecosystem.states.EntityState;
import Model.ecosystem.states.IdleState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@SuppressWarnings("all")
public abstract class Animal extends LivingEntity implements Movable , Eater ,
        EdibleByCarnivore, Sensory , Consumable , Runnable
{

    // ===================== FIELDS =====================
    private FeedingBehavior m_eatBehavior;
    private MovementStrategy m_animalMove;
    private int m_visionRange;

    private BlockingQueue<EcosystemCommand> m_commandQueue;
    private volatile boolean running = true;

    private EntityState m_currentState;

    private Environment m_currentEnv;

    // ===================== Constructors =====================

    // Creates an animal with position, stats, behaviors and command queue
    public Animal(Position position, char symbol , boolean is_alive,BlockingQueue<EcosystemCommand> commandQueue,EntityType entityType, double energy , double maxEnergy , double age ,
                  FeedingBehavior eatBehavior, MovementStrategy animalMove)
    {
        super(position,symbol , is_alive , entityType , energy , maxEnergy , age);
        this.m_eatBehavior = eatBehavior;
        this.m_animalMove = animalMove;
        this.m_visionRange = 2;
        this.m_commandQueue = commandQueue;
        this.m_currentState = new IdleState();
    } //Animal Constructor

    // ===================== Threads Related =====================

    // Runs the animal thread and sends act commands every second
    @Override
    public void run()
    {
        while(running && getIs_alive())
        {
            try
            {
                m_commandQueue.put(new ActCommand(this));
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                running = false;
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        running = false;
    }

    // Allows the animal thread loop to run
    public void startThread()
    {
        running = true;
    }

    // Stops the animal thread loop
    public void stopThread()
    {
        running = false;
    }

    // ===================== Helper Methods =====================

    public boolean isInCorner(Environment env)
    {
        int row = getM_position().getRow();
        int col = getM_position().getCol();

        int maxRow = env.getRows() - 1;
        int maxCol = env.getCols() - 1;

        return (row == 0 && col == 0) || (row == 0 && col == maxCol) || (row == maxRow && col == 0) || (row == maxRow && col == maxCol);
    }



    // ===================== getters & setters =====================


    // Returns the eating behavior of the animal
    public FeedingBehavior getM_eatBehavior()
    {
        return m_eatBehavior;
    }

    // Sets the eating behavior of the animal
    public void setM_eatBehavior(FeedingBehavior m_eatBehavior)
    {
        this.m_eatBehavior = m_eatBehavior;
    }

    // Returns the movement strategy of the animal
    public MovementStrategy getM_animalMove()
    {
        return m_animalMove;
    }

    // Sets the movement strategy of the animal
    public void setM_animalMove(MovementStrategy m_animalMove) {
        this.m_animalMove = m_animalMove;
    }

    // Returns the command queue used by this animal
    public BlockingQueue<EcosystemCommand> getM_commandQueue()
    {
        return this.m_commandQueue;
    }


    //Sets new state for the current state
    public boolean setM_currentState(EntityState newState)
    {
        this.m_currentState = newState;
        return true;
    }

    //Returns the Current State
    public EntityState getM_currentState()
    {
        return this.m_currentState;
    }


    public Environment getM_currentEnv()
    {
        if(this.m_currentEnv == null)
            return null;

        return this.m_currentEnv;
    }
    //Interface Methods
    //Moveable

    // Moves the animal using its movement strategy
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

    // Eats a consumable target and adds energy to the animal
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

    // Gets nearby entities around this animal
    @Override
    public List<AbstractEntity> sense(Environment env) // get all Entities in Distance // Later
    {
        if (env == null)
            return new ArrayList<>();   // import java.util.ArrayList;

        return env.getNearByEntities(this.getM_position());
    }

    //Consumable

    // Returns how much energy this animal gives when eaten
    @Override
    public double getNutritionValue()
    {
        return 0.8f * this.getM_energy();
    }

    // Marks the animal as dead when it gets consumed
    @Override
    public void onConsumed()
    {
        this.setIs_alive(false);
    }

    //Actable (Father)

    // Makes the animal lose energy, try to eat, and move if it did not eat
    @Override
    public void act(Environment env )
    {
        if (!getIs_alive())
            return;

        this.m_currentEnv = env;

        setM_age(getM_age() + 1);

        m_currentState.doAction(this);

        if (getM_energy() <= 0)
            setIs_alive(false);
    }
}