package Model.ecosystem.core;

import Model.ecosystem.commands.ActCommand;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.animals.Animal;
import Model.ecosystem.entities.plants.Plant;
import Model.ecosystem.interfaces.Actable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import Model.ecosystem.interfaces.EcosystemCommand;
import View.Stats.StatsListener;


public class SimulationEngine implements Runnable
{
    // ============ Fields ============
    private Environment m_environment;
    private SimulationStats m_simulationStats;

    private List<StatsListener> m_statsListeners;

    private BlockingQueue<EcosystemCommand> m_commandQueue;
    private volatile boolean running = false;


    private List<Thread> m_entityThreads;
    private Thread m_simulationEngineThread;


    // ============ Constructors ============

    // Creates the simulation engine and initializes all needed components
    public SimulationEngine(Environment environment)
    {
        InitComponents(environment);
    }

    // ============ Methods ============

    // Makes all actable entities act once manually, without starting threads
    public void tick() //
    {

        List<AbstractEntity> copyEntities =
                new ArrayList<>(m_environment.getM_entities());

        for(AbstractEntity entity : copyEntities)
        {
            if(entity instanceof Actable)
            {
                EcosystemCommand command = new ActCommand((Actable) entity);

                command.execute(m_environment);
            }
        }

        afterCommandExecuted();

        System.out.println("Manual tick executed all entities once.");
    }

    // ============ Threaded Methods ============


    // Starts the simulation engine thread and starts a thread for each runnable entity
    public void startSimulation()
    {
        running = true;

        m_simulationEngineThread = new Thread(this);
        m_simulationEngineThread.start();

        List<AbstractEntity> entitiesCopy = new ArrayList<>(m_environment.getM_entities());

        for (AbstractEntity entity : entitiesCopy)
        {
            if(entity instanceof Runnable)
            {
                if(entity instanceof Animal)
                {
                    Thread entityThread = new Thread((Runnable) entity);
                    m_entityThreads.add(entityThread);
                    ((Animal) entity).startThread();

                    entityThread.start();
                    System.out.println(entity.getClass().getSimpleName() +" Thread Started!");
                }
                else if(entity instanceof Plant)
                {
                    Thread entityThread = new Thread((Runnable) entity);
                    m_entityThreads.add(entityThread);
                    ((Plant) entity).startThread();
                    entityThread.start();
                    System.out.println(entity.getClass().getSimpleName() +" Thread Started!");
                }

            }
        }

    }

    // Stops the simulation engine and interrupts all entity threads
    public void stopSimulation()
    {
        running = false;

        if(m_simulationEngineThread != null)
            m_simulationEngineThread.interrupt();

        List<AbstractEntity> entitiesCopy = new ArrayList<>(m_environment.getM_entities());

        for(AbstractEntity entity : entitiesCopy)
        {
            if(entity instanceof Animal)
            {
                ((Animal) entity).stopThread();
            }
        }

        for(Thread thread : m_entityThreads)
        {
            thread.interrupt();
        }

        m_entityThreads.clear();
    }

    // Updates the environment after a command was executed
    private void afterCommandExecuted()
    {
        this.m_environment.removeDeadEntities();
        this.m_simulationStats.update(m_environment);
        notifyStatsChanged();
    }

    // Main loop of the SimulationEngine thread, takes commands from the queue and executes them
    @Override
    public void run()
    {
        while(running)
        {
            try
            {
                EcosystemCommand command = m_commandQueue.take();
                command.execute(m_environment);
                afterCommandExecuted();

            }

            catch (InterruptedException e)
            {
                System.out.println("Stopped Simulation");
                running = false;
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    // ============ Helper Methods ============

    // Initializes the main fields of the simulation engine
    private void InitComponents(Environment environment)
    {
        this.m_environment = environment;
        this.m_simulationStats = new SimulationStats();
        this.m_statsListeners = new ArrayList<>();
        this.m_commandQueue = new LinkedBlockingQueue<>();
        this.m_entityThreads = new ArrayList<>();
    }

    // ============ Observer Methods ============

    // Adds a listener that will be notified when stats change
    public void addStatsListener(StatsListener listener)
    {
        if(listener != null)
            m_statsListeners.add(listener);
    }

    // Notifies all stats listeners that the stats changed
    private void notifyStatsChanged()
    {
        for(StatsListener listener : m_statsListeners)
        {
            listener.onStatsChanged();
        }
    }

    // ============ Getters / Setters ============

    // Returns the current simulation statistics object
    public SimulationStats getSimulationStats()
    {
        return this.m_simulationStats;
    }

    // Replaces the current command queue with a new one
    public boolean setCommandQueue(BlockingQueue<EcosystemCommand> newCommandQueue)
    {
        if(newCommandQueue == null)
            return false;

        this.m_commandQueue = newCommandQueue;
        return true;
    }

    // Returns the command queue used by the simulation
    public BlockingQueue<EcosystemCommand> getCommandQueue()
    {
        return this.m_commandQueue;
    }


    // Clears all waiting commands from the queue
    public void clearCommands()
    {
        this.m_commandQueue.clear();
    }

}