package View.Frame;

import Controller.Buttons.ButtonsController;
import Controller.Map.MapController;
import Factories.EntityFactory;
import Model.ecosystem.core.Environment;
import Model.ecosystem.core.SimulationEngine;
import Model.ecosystem.network.NetworkManager;
import View.Buttons.ButtonsPanel;
import View.Entity.EntitySpawnPanel;
import View.Info.InfoPanel;
import View.Map.MapPanel;
import View.Stats.StatsPanel;

import javax.swing.*;
import java.awt.*;

public class EcoSystemFrame extends JFrame
{
    // ===================== FIELDS =====================

    // Core objects of the simulation
    private Environment m_environment;
    private SimulationEngine m_simulationEngine;
    private NetworkManager m_networkManager;

    // Main panels used in the window
    private MapPanel m_MainMapPanel;
    private ButtonsPanel m_MainButtonsPanel;
    private EntitySpawnPanel m_entitySpawnPanel;
    private StatsPanel m_StatsPanel;
    private InfoPanel m_InfoPanel;


    // ===================== CONSTRUCTORS =====================

    public EcoSystemFrame()
    {
        // Create the main window settings
        createWindow();

        // Use BorderLayout so each panel can sit in a clear area of the frame
        setLayout(new BorderLayout());

        // Create the model/core objects
        initCore();

        // Create and add all the visual panels
        createAllPanels();

        // Final frame adjustments after all components were added
        setAdjustments();
    }

    public EcoSystemFrame(int width, int height)
    {
        // Create the main window with custom size
        createWindow(width, height);

        // Use BorderLayout for organizing the panels
        setLayout(new BorderLayout());

        // Create the model/core objects
        initCore();

        // Create and add all the visual panels
        createAllPanels();

        // Final frame adjustments after all components were added
        setAdjustments();
    }


    // ===================== INIT METHODS =====================

    private void initCore()
    {
        // Environment stores all the entities and map-related simulation data
        m_environment = new Environment();

        // SimulationEngine runs the simulation logic using the same environment
        m_simulationEngine = new SimulationEngine(m_environment);

        // NetworkManager receives portal messages and adds entities to the same environment
        m_networkManager = new NetworkManager(m_environment, m_simulationEngine.getCommandQueue());
    }


    // ===================== WINDOW METHODS =====================

    private void createWindow()
    {
        // Basic frame settings
        setTitle("EcoSystem Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void createWindow(int width, int height)
    {
        // Basic frame settings with custom width and height
        setTitle("EcoSystem Simulation");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void setAdjustments()
    {
        // Pack the frame based on preferred sizes of the panels
        pack();

        // Open the window in the center of the screen
        setLocationRelativeTo(null);

        // Show the frame after everything is ready
        setVisible(true);
    }


    // ===================== PANEL METHODS =====================

    private void createMapPanel()
    {
        // Create the main map panel that draws the map and entities
        m_MainMapPanel = new MapPanel(m_environment);

        // Create the controller that handles mouse actions on the map
        MapController mapController = new MapController(m_MainMapPanel,m_environment,m_InfoPanel);

        // Attach the map controller as the listener for clicks and mouse movement
        m_MainMapPanel.addMouseListener(mapController);
        m_MainMapPanel.addMouseMotionListener(mapController);

        // Add the map to the center of the frame
        add(m_MainMapPanel, BorderLayout.CENTER);
    }

    private void createButtonsPanel()
    {
        // Create the controller that handles all button actions
        ButtonsController buttonsController = new ButtonsController(this, m_simulationEngine, m_environment,m_MainMapPanel);

        // Create the buttons panel and give it the controller
        m_MainButtonsPanel = new ButtonsPanel(buttonsController);

        // Add the buttons panel at the bottom of the frame
        add(m_MainButtonsPanel, BorderLayout.SOUTH);
    }

    private void createEntitySpawnPanel()
    {
        // Create the panel that allows the user to add new entities
        EntityFactory newEntityFactory = new EntityFactory(this.m_simulationEngine.getCommandQueue());
        m_entitySpawnPanel = new EntitySpawnPanel(m_MainMapPanel,newEntityFactory);

        // Set its size and hide it at the start
        m_entitySpawnPanel.setPreferredSize(new Dimension(850, 70));
        m_entitySpawnPanel.setVisible(false);

        // Add the spawn panel on top of the map panel
        m_MainMapPanel.setLayout(new GridBagLayout());
        m_MainMapPanel.add(m_entitySpawnPanel);
    }

    private void createStatsPanel()
    {
        // Create the stats panel and give it the simulation stats object
        m_StatsPanel = new StatsPanel(m_simulationEngine.getSimulationStats());

        // Place the stats panel at the top of the frame
        add(m_StatsPanel,BorderLayout.NORTH);

        // Register the stats panel as a listener so it updates when stats change
        m_simulationEngine.addStatsListener(m_StatsPanel);
    }

    private void createInfoPanel()
    {
        // Create the panel that shows information about the selected entity
        m_InfoPanel = new InfoPanel(m_environment);

        // Place the info panel on the right side of the frame
        add(m_InfoPanel,BorderLayout.EAST);
    }

    private void createAllPanels()
    {
        // Create the panels in an order that makes sense for their dependencies
        createInfoPanel();
        createMapPanel();
        createButtonsPanel();
        createEntitySpawnPanel();
        createStatsPanel();
        startNetworkServer();
    }

    private void startNetworkServer()
    {
        m_networkManager.setOnCommandExecuted(() ->
                SwingUtilities.invokeLater(() -> m_MainMapPanel.repaint()));

        m_networkManager.startServer();
    }


    // ===================== UI METHODS =====================

    public void toggleStatsPanel()
    {
        // Show or hide the stats panel if it already exists
        if(this.m_StatsPanel != null)
        {
            this.m_StatsPanel.toggleStatsPanel();
        }
    }

    public void toggleEntitySpawnPanel()
    {
        // Show or hide the entity spawn panel
        m_entitySpawnPanel.setVisible(!m_entitySpawnPanel.isVisible());

        // Refresh the map panel after changing the spawn panel visibility
        m_MainMapPanel.revalidate();
        m_MainMapPanel.repaint();
    }


    // ===================== GETTERS =====================

    public Environment getM_environment()
    {
        return m_environment;
    }

    public SimulationEngine getM_simulationEngine()
    {
        return m_simulationEngine;
    }

    public MapPanel getM_MainMapPanel()
    {
        return m_MainMapPanel;
    }
}
