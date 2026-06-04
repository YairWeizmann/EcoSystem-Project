package Model.ecosystem.entities.plants;

import Model.ecosystem.commands.ActCommand;
import Model.ecosystem.core.Environment;
import Model.ecosystem.core.Position;
import Model.ecosystem.entities.LivingEntity;
import Model.ecosystem.interfaces.Consumable;
import Model.ecosystem.interfaces.EcosystemCommand;
import Model.ecosystem.interfaces.EdibleByHerbivore;
import Model.ecosystem.interfaces.Reproducible;

import java.util.concurrent.BlockingQueue;

@SuppressWarnings("all")
public abstract class Plant extends LivingEntity implements Consumable , Reproducible, EdibleByHerbivore , Runnable
{

    // ===================== FIELDS =====================
    private double m_growthRate;
    private double m_reproductionChance;
    private BlockingQueue<EcosystemCommand> m_commandQueue;
    private volatile boolean running = true;

    // ===================== Constructors =====================

    // Creates a plant with position, stats, growth values and command queue
    public Plant(Position position, char symbol , boolean is_alive, BlockingQueue<EcosystemCommand> commandQueue, EntityType entityType , double energy , double max_energy , double age
            , double growthRate , double reproductionChance)
    {
        super(position,symbol,is_alive,entityType,energy,max_energy,age);
        setM_growthRate(growthRate);
        setM_reproductionChance(reproductionChance);

        this.m_commandQueue = commandQueue;

    }

    // ===================== Thread Methods =====================

    // Runs the plant thread and sends act commands every second
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
                System.out.print("Stopped " + getM_entityType() + " Thread" + " ");
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        running = false;
    }

    // Allows the plant thread loop to run
    public void startThread()
    {
        running = true;
    }

    // Stops the plant thread loop
    public void stopThread()
    {
        running = false;
    }

    // ===================== InterFace Methods =====================
    //Consumable

    // Returns how much energy this plant gives when eaten
    @Override
    public double getNutritionValue() //How much energy Does that plant give
    {
        return this.getM_energy();
    }


    // Marks the plant as dead when something consumes it
    @Override
    public void onConsumed() //Someone Consumed Planet
    {
        this.setIs_alive(false);
    }

    //Actable

    // Makes the plant grow by adding energy and increasing age
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


    // Returns the plant growth rate
    public double getM_growthRate()
    {
        return m_growthRate;
    }

    // Sets the plant growth rate if the value is valid
    public boolean setM_growthRate(double m_growthRate)
    {
        if(m_growthRate < 0)
            return false;

        this.m_growthRate = m_growthRate;
        return true;
    }

    // Returns the plant reproduction chance
    public double getM_reproductionChance()
    {
        return m_reproductionChance;
    }

    // Sets the plant reproduction chance if the value is valid
    public boolean setM_reproductionChance(double m_reproductionChance)
    {
        if(m_reproductionChance < 0)
            return false;

        this.m_reproductionChance = m_reproductionChance;
        return true;
    }

    // Returns the command queue used by this plant
    public BlockingQueue<EcosystemCommand> getM_commandQueue()
    {
        return this.m_commandQueue;
    }
}