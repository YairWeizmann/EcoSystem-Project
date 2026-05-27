package View.Stats;

import Factories.TextFactory;
import Model.ecosystem.core.SimulationEngine;
import Model.ecosystem.core.SimulationStats;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StatsPanel extends JPanel implements StatsListener
{
    // ===================== FIELDS =====================
    private JLabel m_ticks;
    private JLabel m_entityCount;
    private JLabel m_totalEnergy;


    private BufferedImage m_backgroundImage;
    private SimulationStats m_simulationStats;
    // ===================== Constructors =====================

    public StatsPanel(SimulationStats simulationStats)
    {
        InitComponents(simulationStats);
        loadBackgroundImage();
    }

    // ===================== Helper Methods =====================
    private void InitComponents(SimulationStats simulationStats)
    {
        m_ticks = TextFactory.createStylizedLabel("Ticks", Color.white,Font.BOLD,16);
        m_entityCount = TextFactory.createStylizedLabel("Entities" , Color.white,Font.BOLD,16);
        m_totalEnergy = TextFactory.createStylizedLabel("Energy",Color.white,Font.BOLD,16);

        add(m_ticks);
        add(m_entityCount);
        add(m_totalEnergy);

        setPreferredSize(new Dimension(650, 55));
        setVisible(false);

        this.m_simulationStats = simulationStats;

    }

    // ===================== Stats Logic Methods =====================
    public void showStats()
    {
        m_ticks.setText("Ticks: " + m_simulationStats.getM_ticks());
        m_entityCount.setText("Entities: " + m_simulationStats.getM_totalEntities());
        m_totalEnergy.setText("Total Energy: " + m_simulationStats.getM_totalEnergy());

    }

    public void toggleStatsPanel()
    {
        setVisible(!this.isVisible());

        if(this.isVisible())
            showStats();
    }

    @Override
    public void onStatsChanged()
    {
        showStats();
    }



    // ===================== Background / Draw Methods =====================

    @Override
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        if(m_backgroundImage != null)
        {
            graphics.drawImage(m_backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
    }




    private void loadBackgroundImage()
    {
        try
        {
            m_backgroundImage = ImageIO.read(new File("src/Assets/Background/WoodPanel.png"));
        }
        catch(IOException e)
        {
            System.out.println("Failed to load wood background.");
            e.printStackTrace();
        }
    }



}
