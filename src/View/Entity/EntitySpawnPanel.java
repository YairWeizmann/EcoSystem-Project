package View.Entity;

import Model.ecosystem.core.Position;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.animals.Deer;
import Model.ecosystem.entities.animals.Lion;
import Model.ecosystem.entities.animals.Rabbit;
import Model.ecosystem.entities.plants.Flower;
import Model.ecosystem.entities.plants.OakTree;
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

    private JComboBox<AbstractEntity.EntityType> m_entityTypeComboBox;
    private JTextField m_energyField;
    private JTextField m_positionFieldX;
    private JTextField m_positionFieldY;
    private JButton m_addButton;

    private BufferedImage m_backgroundImage;
    private MapPanel m_mapPanel;


    // ===================== CONSTRUCTORS =====================

    public EntitySpawnPanel(MapPanel mapPanel)
    {
        this.m_mapPanel = mapPanel;

        setPreferredSize(new Dimension(700, 55));
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 12));

        initComponents();
        loadBackgroundImage();

        add(new JLabel("Entity Type:"));
        add(m_entityTypeComboBox);

        add(new JLabel("Energy:"));
        add(m_energyField);

        add(new JLabel("Position X:"));
        add(m_positionFieldX);

        add(new JLabel("Position Y:"));
        add(m_positionFieldY);

        add(m_addButton);
    }


    // ===================== INIT METHODS =====================

    private void initComponents()
    {
        m_energyField = new JTextField(4);
        m_positionFieldX = new JTextField(4);
        m_positionFieldY = new JTextField(4);

        m_entityTypeComboBox = new JComboBox<>(AbstractEntity.EntityType.values());

        m_addButton = new JButton("Add Entity");
        m_addButton.addActionListener(this::onClickSpawnEntity);
    }


    // ===================== PAINT METHODS =====================

    @Override
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

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
            int posX = Integer.parseInt(m_positionFieldX.getText()); // col
            int posY = Integer.parseInt(m_positionFieldY.getText()); // row
            int energy = Integer.parseInt(m_energyField.getText());

            AbstractEntity.EntityType entityType =
                    (AbstractEntity.EntityType) m_entityTypeComboBox.getSelectedItem();

            Position spawnedEntityPos = new Position(posY, posX); // row, col

            AbstractEntity spawnedEntity = createEntity(entityType, spawnedEntityPos, energy);

            if(spawnedEntity != null)
            {
                m_mapPanel.spawnEntity(spawnedEntity);
            }
        }
        catch(NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this, "Enter valid numbers");
        }
    }

    private AbstractEntity createEntity(AbstractEntity.EntityType entityType, Position position, int energy)
    {
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

            default:
                return null;
        }
    }


    // ===================== VISUAL METHODS =====================

    private void loadBackgroundImage()
    {
        try
        {
            m_backgroundImage = ImageIO.read(new File("src/Assets/Background/EntitySpawnPanelBack.png"));
        }
        catch(IOException e)
        {
            System.out.println("Failed to load wood background.");
            e.printStackTrace();
        }
    }
}