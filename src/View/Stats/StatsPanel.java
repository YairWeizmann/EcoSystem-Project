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

    // Labels that show the current simulation statistics
    private JLabel m_ticks;
    private JLabel m_entityCount;
    private JLabel m_totalEnergy;

    // Background image for the stats panel
    private BufferedImage m_backgroundImage;

    // Holds the actual stats data from the simulation
    private SimulationStats m_simulationStats;


    // ===================== Constructors =====================

    public StatsPanel(SimulationStats simulationStats)
    {
        // Create the labels and connect the stats object
        InitComponents(simulationStats);

        // Load the background image of the panel
        loadBackgroundImage();
    }


    // ===================== Helper Methods =====================

    private void InitComponents(SimulationStats simulationStats)
    {
        // Create the labels with the same text style
        m_ticks = TextFactory.createStylizedLabel("Ticks", Color.white,Font.BOLD,16);
        m_entityCount = TextFactory.createStylizedLabel("Entities" , Color.white,Font.BOLD,16);
        m_totalEnergy = TextFactory.createStylizedLabel("Energy",Color.white,Font.BOLD,16);

        // Add the labels to the panel
        add(m_ticks);
        add(m_entityCount);
        add(m_totalEnergy);

        // Set the panel size
        setPreferredSize(new Dimension(650, 55));

        // Start hidden, and show it only when the user opens the stats panel
        setVisible(false);

        // Save the stats object so the panel can read the current values
        this.m_simulationStats = simulationStats;
    }


    // ===================== Stats Logic Methods =====================

    public void showStats()
    {
        // Update the labels with the current simulation stats
        m_ticks.setText("Ticks: " + m_simulationStats.getM_ticks());
        m_entityCount.setText("Entities: " + m_simulationStats.getM_totalEntities());
        m_totalEnergy.setText("Total Energy: " + m_simulationStats.getM_totalEnergy());
    }

    public void toggleStatsPanel()
    {
        // Show the panel if it is hidden, or hide it if it is already visible
        setVisible(!this.isVisible());

        // If the panel was just opened, refresh the stats immediately
        if(this.isVisible())
            showStats();
    }

    @Override
    public void onStatsChanged()
    {
        // This method is called by the simulation when the stats change
        showStats();
    }


    // ===================== Background / Draw Methods =====================

    @Override
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        // Draw the background image if it was loaded successfully
        if(m_backgroundImage != null)
        {
            graphics.drawImage(m_backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
    }

    private void loadBackgroundImage()
    {
        try
        {
            // Load the wood background image for the stats panel
            m_backgroundImage = ImageIO.read(new File("src/Assets/Background/WoodPanel.png"));
        }
        catch(IOException e)
        {
            // Print an error if the background image could not be loaded
            System.out.println("Failed to load wood background.");
            e.printStackTrace();
        }
    }
}