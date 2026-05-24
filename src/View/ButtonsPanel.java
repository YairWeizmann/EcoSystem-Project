package View.Buttons;

import Model.ecosystem.core.SimulationEngine;
import View.EcoSystemFrame;
import View.Map.MapPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ButtonsPanel extends JPanel
{
    // ===================== FIELDS =====================
    private JButton m_tickButton;
    private JButton m_runButton;
    private JButton m_stopButton;
    private JButton m_resetButton;
    private JButton m_addEntityButton;

    private EcoSystemFrame m_frame;
    private SimulationEngine m_simulationEngine;
    private MapPanel m_mapPanel;

    private Timer m_runTimer;


    // ===================== CONSTRUCTORS =====================
    public ButtonsPanel(EcoSystemFrame frame, SimulationEngine simulationEngine, MapPanel mapPanel)
    {
        this.m_frame = frame;
        this.m_simulationEngine = simulationEngine;
        this.m_mapPanel = mapPanel;

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        initComponents();
        addComponents();
        initTimer();
    }


    // ===================== INIT METHODS =====================
    private void initComponents()
    {
        m_tickButton = new JButton("Tick");
        m_runButton = new JButton("Run");
        m_stopButton = new JButton("Stop");
        m_resetButton = new JButton("Reset");
        m_addEntityButton = new JButton("Add Entity");

        m_tickButton.addActionListener(this::onClickTick);
        m_runButton.addActionListener(this::onClickRun);
        m_stopButton.addActionListener(this::onClickStop);
        m_resetButton.addActionListener(this::onClickReset);
        m_addEntityButton.addActionListener(this::onClickAddEntity);
    }

    private void addComponents()
    {
        add(m_tickButton);
        add(m_runButton);
        add(m_stopButton);
        add(m_resetButton);
        add(m_addEntityButton);
    }

    private void initTimer()
    {
        m_runTimer = new Timer(500, e -> {
            m_simulationEngine.tick();
            m_mapPanel.repaint();
        });
    }


    // ===================== BUTTON METHODS =====================
    private void onClickTick(ActionEvent event)
    {
        m_simulationEngine.tick();
        m_mapPanel.repaint();
    }

    private void onClickRun(ActionEvent event)
    {
        if(!m_runTimer.isRunning())
            m_runTimer.start();
    }

    private void onClickStop(ActionEvent event)
    {
        if(m_runTimer.isRunning())
            m_runTimer.stop();
    }

    private void onClickReset(ActionEvent event)
    {
        System.out.println("Reset clicked");
        // later: clear environment and repaint map
    }

    private void onClickAddEntity(ActionEvent event)
    {
        m_frame.toggleEntitySpawnPanel();
    }
}