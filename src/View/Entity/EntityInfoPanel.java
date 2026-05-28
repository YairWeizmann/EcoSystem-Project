package View.Entity;

import Factories.TextFactory;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.LivingEntity;
import Model.ecosystem.entities.StaticEntity;

import javax.swing.*;
import java.awt.*;

public class EntityInfoPanel extends JPanel
{
    // ===================== FIELDS =====================

    // Labels that show the entity details when hovering over it
    private JLabel m_name;
    private JLabel m_energy;
    private JLabel m_age;
    private JLabel m_isalive;


    // ===================== CONSTRUCTORS =====================

    public EntityInfoPanel()
    {
        // Create and style all the labels in the panel
        InitComponents();

        // Show the labels in rows, one under another
        setLayout(new GridLayout(3, 1));

        // The panel starts hidden and only appears when hovering an entity
        setVisible(false);
    }


    // ===================== HELPER METHODS =====================

    private void InitComponents()
    {
        // Create the labels with the same text style
        m_name = TextFactory.createStylizedLabel("Name: ",Color.white,Font.BOLD,16);
        m_energy = TextFactory.createStylizedLabel("Energy: ",Color.white,Font.BOLD,16);
        m_age = TextFactory.createStylizedLabel("Age: ",Color.white,Font.BOLD,16);
        m_isalive = TextFactory.createStylizedLabel("Is Alive: ",Color.white,Font.BOLD,16);

        // Add the labels to the panel
        add(m_name);
        add(m_energy);
        add(m_age);
        add(m_isalive);

        // Make the background black with some transparency
        setBackground(new Color(0, 0, 0, 160));
        setOpaque(true);
    }


    public void showEntity(AbstractEntity entity)
    {
        // If there is no entity, hide the panel
        if(entity == null)
        {
            toggleHide();
            return;
        }

        // These will be used depending on what kind of entity we got
        LivingEntity livingEntity = null;
        StaticEntity staticEntity = null;

        // Check if the entity has living stats like energy and age
        if(entity instanceof LivingEntity)
            livingEntity = (LivingEntity)entity;

            // If it is not living, check if it is a static entity
        else if(entity instanceof StaticEntity)
            staticEntity = (StaticEntity) entity;

        if(livingEntity != null)
        {
            // Show info for living entities
            m_name.setText("Name:" + entity.getM_entityType());
            m_energy.setText("Energy: " + livingEntity.getM_energy());
            m_age.setText("Age: " + livingEntity.getM_age());
            m_isalive.setText("Is Alive: " + livingEntity.getIs_alive());
        }

        else if(staticEntity != null)
        {
            // Static entities do not really have energy or age like animals/plants
            m_name.setText("Name:  " + staticEntity.getM_entityType());
            m_energy.setText("Energy:  0");
            m_age.setText("Age: None ");
            m_isalive.setText("Is Alive: False");

        }

        // Show the panel after the labels were updated
        setVisible(true);
    }

    public void toggleHide()
    {
        // Hide the panel when there is no entity to show
        setVisible(false);
    }

}