package Controller.Buttons;

import Model.ecosystem.core.Environment;
import Model.ecosystem.core.SimulationEngine;
import View.Frame.EcoSystemFrame;
import View.Map.MapPanel;

import javax.swing.*;

public class ButtonsController
{

    // ===================== FIELDS =====================

    // Holds the main frame so we can open/close panels from button clicks
    private EcoSystemFrame m_frame;

    // The simulation engine is responsible for running the simulation ticks
    private SimulationEngine m_simulationEngine;

    // The environment holds all the entities in the simulation
    private Environment m_environment;

    // The map panel is repainted after changes so the user can see the updates
    private MapPanel m_mapPanel;

    // Timer used for running the simulation automatically every few milliseconds
    private Timer m_runTimer;


    // ===================== CONSTRUCTORS =====================

    public ButtonsController(EcoSystemFrame frame, SimulationEngine simulationEngine, Environment environment, MapPanel mapPanel)
    {
        // Save references to the main parts that the buttons need to control
        this.m_frame = frame;
        this.m_simulationEngine = simulationEngine;
        this.m_environment = environment;
        this.m_mapPanel = mapPanel;

    }


    // ===================== BUTTON ACTIONS =====================

    public void onTickClicked()
    {
        // Run only one simulation step
        m_simulationEngine.tick();

        // Redraw the map after the tick
        m_mapPanel.repaint();
    }

    public void onRunClicked()
    {
        // Start automatic ticking only if the timer is not already running
       m_simulationEngine.startSimulation();
    }

    public void onStopClicked()
    {
        // Stop the automatic simulation run
        m_simulationEngine.stopSimulation();
    }

    public void onResetClicked()
    {
        //Stops all Entities Threads
        m_simulationEngine.stopSimulation();

        //Clears the BlockingQueue Commands
        m_simulationEngine.clearCommands();

        // Clear all entities from the environment
        m_environment.resetEntities();

        // Reset the statistics back to their starting values
        m_simulationEngine.getSimulationStats().reset();

        // Redraw the empty/reset map
        m_mapPanel.repaint();
    }

    public void onAddEntityClicked()
    {
        // Show or hide the entity spawn panel
        m_frame.toggleEntitySpawnPanel();
    }

    public void onStatsClicked()
    {
        // Show or hide the statistics panel
        m_frame.toggleStatsPanel();
    }
}