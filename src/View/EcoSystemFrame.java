package View;

import Model.ecosystem.core.Environment;
import Model.ecosystem.core.SimulationEngine;
import View.Buttons.ButtonsPanel;
import View.Entity.EntitySpawnPanel;
import View.Map.MapPanel;

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


    // ===================== CONSTRUCTORS =====================
    public EcoSystemFrame()
    {
        createWindow();
        setLayout(new BorderLayout());

        initCore();

        createMapPanel();
        createButtonsPanel();
        createEntitySpawnPanel();

        setAdjustments();
    }

    public EcoSystemFrame(int width, int height)
    {
        createWindow(width, height);
        setLayout(new BorderLayout());

        initCore();

        createMapPanel();
        createButtonsPanel();
        createEntitySpawnPanel();

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
        m_MainMapPanel = new MapPanel(m_environment);
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


    // ===================== UI METHODS =====================
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