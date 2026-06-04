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
    public Plant(Position position, char symbol , boolean is_alive, BlockingQueue<EcosystemCommand> commandQueue, EntityType entityType , double energy , double max_energy , double age
            , double growthRate , double reproductionChance)
    {
      super(position,symbol,is_alive,entityType,energy,max_energy,age);
      setM_growthRate(growthRate);
      setM_reproductionChance(reproductionChance);

      this.m_commandQueue = commandQueue;

    }

    // ===================== Thread Methods =====================
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
    public void startThread()
    {
        running = true;
    }

    public void stopThread()
    {
        running = false;
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

    public BlockingQueue<EcosystemCommand> getM_commandQueue()
    {
        return this.m_commandQueue;
    }
}
