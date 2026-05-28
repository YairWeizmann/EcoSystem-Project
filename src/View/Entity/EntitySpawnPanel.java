package View.Entity;

import Factories.TextFactory;
import Model.ecosystem.core.Position;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.animals.Deer;
import Model.ecosystem.entities.animals.Lion;
import Model.ecosystem.entities.animals.Rabbit;
import Model.ecosystem.entities.plants.Flower;
import Model.ecosystem.entities.plants.OakTree;
import Model.ecosystem.entities.resources.Rock;
import Model.ecosystem.entities.resources.Water;
import View.Map.MapPanel;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EntitySpawnPanel extends JPanel
{
    // ===================== FIELDS =====================

    // Combo box for choosing what type of entity to spawn
    private JComboBox<AbstractEntity.EntityType> m_entityTypeComboBox;

    // Text fields for the values the user enters
    private JTextField m_energyField;
    private JTextField m_positionFieldX;
    private JTextField m_positionFieldY;

    // Button that actually spawns the entity
    private JButton m_addButton;

    // Background image for the panel
    private BufferedImage m_backgroundImage;

    // Reference to the map panel, so we can add the new entity to the map
    private MapPanel m_mapPanel;


    // ===================== CONSTRUCTORS =====================

    public EntitySpawnPanel(MapPanel mapPanel)
    {
        // Save the map panel because the spawned entity needs to be sent there
        this.m_mapPanel = mapPanel;

        // Basic size and layout for the spawn panel
        setPreferredSize(new Dimension(400, 100));
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 12));

        // Create all UI components and load the background
        initComponents();
        loadBackgroundImage();
    }


    // ===================== INIT METHODS =====================

    private void initComponents()
    {
        // Create text fields with small width because the values are short
        m_energyField = new JTextField(3);
        m_positionFieldX = new JTextField(3);
        m_positionFieldY = new JTextField(3);

        // Fill the combo box with all possible entity types
        m_entityTypeComboBox = new JComboBox<>(AbstractEntity.EntityType.values());

        // Create the add button and connect it to the spawn method
        m_addButton = new JButton("Add Entity");
        m_addButton.addActionListener(this::onClickSpawnEntity);

        // Add labels and inputs to the panel
        add(TextFactory.createStylizedLabel("Entity Type",Color.white,Font.BOLD,16));
        add(m_entityTypeComboBox);

        add((TextFactory.createStylizedLabel("Energy",Color.white,Font.BOLD,16)));
        add(m_energyField);

        add((TextFactory.createStylizedLabel("Pos X",Color.white,Font.BOLD,16)));
        add(m_positionFieldX);

        add((TextFactory.createStylizedLabel("Pos Y",Color.white,Font.BOLD,16)));
        add(m_positionFieldY);

        add(m_addButton);
    }


    // ===================== PAINT METHODS =====================

    @Override
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        // Draw the panel background if the image was loaded successfully
        if(m_backgroundImage != null)
        {
            graphics.drawImage(m_backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
    }


    // ===================== ENTITY SPAWN METHODS =====================

    private void onClickSpawnEntity(ActionEvent event)
    {
        try
        {
            // In the UI, X means column and Y means row
            int posX = Integer.parseInt(m_positionFieldX.getText()); // col
            int posY = Integer.parseInt(m_positionFieldY.getText()); // row

            // Parse energy from the text field
            int energy = Integer.parseInt(m_energyField.getText());

            // Get the selected entity type from the combo box
            AbstractEntity.EntityType entityType =
                    (AbstractEntity.EntityType) m_entityTypeComboBox.getSelectedItem();

            // Position constructor gets row first, then col
            Position spawnedEntityPos = new Position(posY, posX); // row, col

            // Create the correct entity object according to the selected type
            AbstractEntity spawnedEntity = createEntity(entityType, spawnedEntityPos, energy);

            // If the entity was created successfully, add it to the map
            if(spawnedEntity != null)
            {
                m_mapPanel.spawnEntity(spawnedEntity);
            }
        }
        catch(NumberFormatException ex)
        {
            // Happens if the user entered text instead of numbers
            JOptionPane.showMessageDialog(this, "Enter valid numbers");
        }
    }

    private AbstractEntity createEntity(AbstractEntity.EntityType entityType, Position position, int energy)
    {
        // Create the correct subclass based on the selected entity type
        switch(entityType)
        {
            case Deer:
                return new Deer(position);

            case Rabbit:
                return new Rabbit(position);

            case Lion:
                return new Lion(position);

            case OakTree:
                return new OakTree(position);

            case Flower:
                return new Flower(position);

            case Water:
                return new Water(position);

            case Rock:
                return new Rock(position);

            default:
                return null;
        }
    }


    // ===================== VISUAL METHODS =====================

    private void loadBackgroundImage()
    {
        try
        {
            // Load the wood background image for this panel
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