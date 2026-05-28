package View.Info;

import Factories.TextFactory;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.LivingEntity;
import Model.ecosystem.entities.StaticEntity;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel
{
    // ===================== FIELDS =====================

    // Labels that show the selected entity information
    private JLabel m_name;
    private JLabel m_energy;
    private JLabel m_age;
    private JLabel m_isalive;


    // ===================== Constructors =====================

    public InfoPanel()
    {
        // Create and set up all the labels and panel settings
        InitComponents();
    }


    // ===================== Helper Methods =====================

    private void InitComponents()
    {
        // Create the labels with the same text style
        m_name = TextFactory.createStylizedLabel("Name: ", Color.white,Font.BOLD,16);
        m_energy = TextFactory.createStylizedLabel("Energy: ",Color.white,Font.BOLD,16);
        m_age = TextFactory.createStylizedLabel("Age: ",Color.white,Font.BOLD,16);
        m_isalive = TextFactory.createStylizedLabel("Is Alive:  ",Color.white,Font.BOLD,16);

        // Add the labels to the panel
        add(m_name);
        add(m_energy);
        add(m_age);
        add(m_isalive);

        // Set the size of the info panel
        setPreferredSize(new Dimension(150, 100));

        // Show each label in a separate row
        setLayout(new GridLayout(4, 1)); // 4 rows, 1 column

        // Give the panel a dark transparent background
        setBackground(new Color(0, 0, 0, 100));
        setOpaque(true);

        // Start hidden until the user clicks an entity
        setVisible(false);
    }


    public void showInfo(AbstractEntity entity)
    {
        // If no entity was found on the selected tile, hide the panel
        if(entity == null)
        {
            setVisible(false);
            return;
        }

        if((entity instanceof LivingEntity))
        {
            LivingEntity livingEntity = null;

            // Show the panel because we have something to display
            setVisible(true);

            // Cast to LivingEntity so we can access energy and age
            livingEntity = (LivingEntity) entity;

            // Update the labels with the living entity data
            m_name.setText("Name:  " + entity.getM_entityType());
            m_energy.setText("Energy:  " + livingEntity.getM_energy());
            m_age.setText("Age:  " + livingEntity.getM_age());
            m_isalive.setText("Is Alive:  " + livingEntity.getIs_alive());
        }
        else if(entity instanceof StaticEntity)
        {
            StaticEntity staticEntity = null;

            // Show the panel because we have a static entity to display
            setVisible(true);

            // Cast to StaticEntity for clarity
            staticEntity = (StaticEntity) entity;

            // Static entities do not have real energy or age, so show default values
            m_name.setText("Name:  " + entity.getM_entityType());
            m_energy.setText("Energy: 0");
            m_age.setText("Age: None");
            m_isalive.setText("Is Alive: False");
        }
    }
}