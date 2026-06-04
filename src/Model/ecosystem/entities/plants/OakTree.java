package Model.ecosystem.entities.plants;

import Model.ecosystem.core.Environment;
import Model.ecosystem.core.Position;
import Model.ecosystem.interfaces.EcosystemCommand;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

@SuppressWarnings("all")
public class OakTree extends Plant
{

    // ===================== FIELDS =====================
    private static final char m_symbol = 'T';
    private static final boolean m_is_alive = true;
    private static final double m_energy = 80f;
    private static final double m_maxEnergy = 120f;
    private static final int m_age = 0;
    private static final int m_growthRate = 2;
    private static final double m_reproductionChance = 0.05f;

    // ===================== Constructors =====================
    public OakTree(Position position, char symbol, boolean is_alive,BlockingQueue<EcosystemCommand> commandQueue, double energy, double max_energy, double age, double growthRate, double reproductionChance)
    {
        super(position, symbol, is_alive,commandQueue,EntityType.OakTree, energy = 80f, max_energy = 120, age, growthRate = 2, reproductionChance = 0.05f);
    }
    public OakTree(Position position , BlockingQueue<EcosystemCommand> commandQueue)
    {
        super(position, m_symbol,true,commandQueue,EntityType.OakTree , m_energy, m_maxEnergy, m_age, m_growthRate,m_reproductionChance);
    }
    // ===================== Interfaces Methods =====================
    //Reproducible
    @Override
    public boolean reproduce(Environment env)
    {
       Random randomgenerator = new Random();
       double tryToReproduce = randomgenerator.nextDouble(0f,1f);

       if(tryToReproduce <= m_reproductionChance)
           return true;

        return false;
    }

    @Override
    public void act(Environment env)
    {
        super.act(env);

        if(this.getIs_alive())
        {
            boolean sucessReproduction = reproduce(env);

            if(sucessReproduction)
            {
                int distanceFromOakTree = 1;
                Position newPos = env.getFreeNearbyPos(this.getM_position(),distanceFromOakTree);

                if(newPos == null)
                    return;

                OakTree newOakTree = new OakTree(newPos,getM_commandQueue());
                env.addEntity(newOakTree);

            }
        }
    }



}
