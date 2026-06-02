package View.Buttons;

import Controller.Buttons.ButtonsController;
import Model.ecosystem.core.SimulationEngine;
import View.Frame.EcoSystemFrame;
import View.Map.MapPanel;

import javax.swing.*;
import java.awt.*;

public class ButtonsPanel extends JPanel
{
    // ===================== FIELDS =====================

    // Main simulation control buttons
    private JButton m_tickButton;
    private JButton m_runButton;
    private JButton m_stopButton;
    private JButton m_resetButton;
    private JButton m_addEntityButton;
    private JButton m_statsButton;

    // References kept from the older structure / available if needed later
    private EcoSystemFrame m_frame;
    private SimulationEngine m_simulationEngine;
    private MapPanel m_mapPanel;

    // Timer reference, currently handled by the controller
    private Timer m_runTimer;

    // Controller that handles what each button actually does
    ButtonsController m_buttonsController;


    // ===================== CONSTRUCTORS =====================

    public ButtonsPanel(ButtonsController buttonsController)
    {
        // Save the controller so the panel can forward button clicks to it
        this.m_buttonsController = buttonsController;

        // Simple layout that places the buttons in one row with spacing
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        initComponents();
        addComponents();
    }


    // ===================== INIT METHODS =====================

    private void initComponents()
    {
        // Create all the buttons that control the simulation
        m_tickButton = new JButton("Tick");
        m_runButton = new JButton("Run");
        m_stopButton = new JButton("Stop");
        m_resetButton = new JButton("Reset");
        m_addEntityButton = new JButton("Add Entity");
        m_statsButton = new JButton("Stats");

        // Connect each button to the matching controller method
        m_tickButton.addActionListener(e -> m_buttonsController.onTickClicked());
        m_runButton.addActionListener(e -> m_buttonsController.onRunClicked());
        m_stopButton.addActionListener(e -> m_buttonsController.onStopClicked());
        m_resetButton.addActionListener(e -> m_buttonsController.onResetClicked());
        m_addEntityButton.addActionListener(e -> m_buttonsController.onAddEntityClicked());
        m_statsButton.addActionListener(e -> m_buttonsController.onStatsClicked());
    }

    private void addComponents()
    {
        // Add the buttons to the panel in the order they should appear
        add(m_tickButton);
        add(m_runButton);
        add(m_stopButton);
        add(m_resetButton);
        add(m_addEntityButton);
        add(m_statsButton);
    }
}