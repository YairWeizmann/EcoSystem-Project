package View.Info;

import Controller.Network.PortalController;
import Factories.TextFactory;
import Model.ecosystem.core.Environment;
import Model.ecosystem.decorators.PoisonedDecorator;
import Model.ecosystem.decorators.SpeedDecorator;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.LivingEntity;
import Model.ecosystem.entities.StaticEntity;
import Model.ecosystem.entities.animals.Animal;
import Model.ecosystem.interfaces.Actable;

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

    private JButton m_poisonButton;
    private JButton m_speedButton;
    private JButton m_portalButton;
    private JTextField m_targetIpField;

    private AbstractEntity m_selectedEntity;
    private Environment m_environment;
    private PortalController m_portalController;


    // ===================== Constructors =====================

    public InfoPanel(Environment environment)
    {
        this.m_environment = environment;

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

        m_poisonButton = new JButton("Apply Poison");
        m_speedButton = new JButton("Apply Speed");
        m_portalButton = new JButton("Send to Port");
        m_targetIpField = new JTextField();

        m_poisonButton.setEnabled(false);
        m_speedButton.setEnabled(false);
        m_portalButton.setEnabled(false);

        m_poisonButton.addActionListener(e -> applyPoison());
        m_speedButton.addActionListener(e -> applySpeed());
        m_portalButton.addActionListener(e -> sendToPortal());

        // Add the labels to the panel
        add(m_name);
        add(m_energy);
        add(m_age);
        add(m_isalive);
        add(m_poisonButton);
        add(m_speedButton);
        add(TextFactory.createStylizedLabel("Target IP:",Color.white,Font.BOLD,14));
        add(m_targetIpField);
        add(m_portalButton);

        // Set the size of the info panel
        setPreferredSize(new Dimension(170, 240));

        // Show each label in a separate row
        setLayout(new GridLayout(9, 1)); // 9 rows, 1 column

        // Give the panel a dark transparent background
        setBackground(new Color(0, 0, 0, 100));
        setOpaque(true);

        // Start hidden until the user clicks an entity
        setVisible(false);
    }


    private void applyPoison()
    {
        if (!(m_selectedEntity instanceof Actable))
            return;

        PoisonedDecorator poisoned = new PoisonedDecorator((Actable) m_selectedEntity);

        m_environment.replaceEntity(m_selectedEntity, poisoned);

        m_selectedEntity = poisoned;

        System.out.println("Poison act called");
    }


    private void applySpeed()
    {
        if (!(m_selectedEntity instanceof Actable))
            return;

        SpeedDecorator speedBoost = new SpeedDecorator((Actable) m_selectedEntity);

        m_environment.replaceEntity(m_selectedEntity, speedBoost);

        m_selectedEntity = speedBoost;

        System.out.println("Speed Been applied to " + m_selectedEntity.getClass().getSimpleName());
    }

    private void sendToPortal()
    {
        if (m_portalController == null)
        {
            System.out.println("Portal controller was not connected.");
            return;
        }

        boolean sent = m_portalController.sendToPortal(m_selectedEntity, m_targetIpField.getText());

        if (sent)
        {
            showInfo(null);
        }
    }


    public void showInfo(AbstractEntity entity)
    {
        m_selectedEntity = entity;

        // If no entity was found on the selected tile, hide the panel
        if(entity == null)
        {
            m_poisonButton.setEnabled(false);
            m_speedButton.setEnabled(false);
            m_portalButton.setEnabled(false);

            setVisible(false);
            return;
        }

        boolean canDecorate = entity instanceof Actable;
        boolean canSendToPortal = entity instanceof Animal;

        m_poisonButton.setEnabled(canDecorate);
        m_speedButton.setEnabled(canDecorate);
        m_portalButton.setEnabled(canSendToPortal);

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

    public void setPortalController(PortalController portalController)
    {
        this.m_portalController = portalController;
    }
}
