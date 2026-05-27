package View.Frame;

import Model.ecosystem.core.Environment;
import Model.ecosystem.core.SimulationEngine;
import View.Buttons.ButtonsPanel;
import View.Entity.EntityInfoPanel;
import View.Entity.EntitySpawnPanel;
import View.Info.InfoPanel;
import View.Map.MapPanel;
import View.Stats.StatsPanel;

import javax.swing.*;
import java.awt.*;

public class EcoSystemFrame extends JFrame
{
    // ===================== FIELDS =====================

    // Core
    private Environment m_environment;
    private SimulationEngine m_simulationEngine;

    // Panels
    private MapPanel m_MainMapPanel;
    private ButtonsPanel m_MainButtonsPanel;
    private EntitySpawnPanel m_entitySpawnPanel;
    private StatsPanel m_StatsPanel;
    private InfoPanel m_InfoPanel;


    // ===================== CONSTRUCTORS =====================
    public EcoSystemFrame()
    {
        createWindow();
        setLayout(new BorderLayout());

        initCore();
        createAllPanels();

        setAdjustments();
    }

    public EcoSystemFrame(int width, int height)
    {
        createWindow(width, height);
        setLayout(new BorderLayout());

        initCore();
        createAllPanels();
        setAdjustments();
    }


    // ===================== INIT METHODS =====================
    private void initCore()
    {
        m_environment = new Environment();
        m_simulationEngine = new SimulationEngine(m_environment);
    }


    // ===================== WINDOW METHODS =====================
    private void createWindow()
    {
        setTitle("EcoSystem Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void createWindow(int width, int height)
    {
        setTitle("EcoSystem Simulation");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void setAdjustments()
    {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    // ===================== PANEL METHODS =====================
    private void createMapPanel()
    {
        m_MainMapPanel = new MapPanel(m_environment,m_InfoPanel);
        add(m_MainMapPanel, BorderLayout.CENTER);
    }

    private void createButtonsPanel()
    {
        m_MainButtonsPanel = new ButtonsPanel(this, m_simulationEngine, m_MainMapPanel);
        add(m_MainButtonsPanel, BorderLayout.SOUTH);
    }



    private void createEntitySpawnPanel()
    {
        m_entitySpawnPanel = new EntitySpawnPanel(m_MainMapPanel);
        m_entitySpawnPanel.setPreferredSize(new Dimension(850, 70));
        m_entitySpawnPanel.setVisible(false);

        m_MainMapPanel.setLayout(new GridBagLayout());
        m_MainMapPanel.add(m_entitySpawnPanel);
    }

    private void createStatsPanel()
    {
        m_StatsPanel = new StatsPanel(m_simulationEngine.getSimulationStats());
        add(m_StatsPanel,BorderLayout.NORTH);

        m_simulationEngine.addStatsListener(m_StatsPanel);
    }

    private void createInfoPanel()
    {
        m_InfoPanel = new InfoPanel();
        add(m_InfoPanel,BorderLayout.EAST);
    }


    private void createAllPanels()
    {
        createInfoPanel();
        createMapPanel();
        createButtonsPanel();
        createEntitySpawnPanel();
        createStatsPanel();
    }

    // ===================== UI METHODS =====================
    public void toggleStatsPanel()
    {
        if(this.m_StatsPanel != null)
        {
            this.m_StatsPanel.toggleStatsPanel();
        }
    }

    public void toggleEntitySpawnPanel()
    {
        m_entitySpawnPanel.setVisible(!m_entitySpawnPanel.isVisible());

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