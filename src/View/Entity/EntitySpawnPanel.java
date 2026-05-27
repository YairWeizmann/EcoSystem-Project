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

        setPreferredSize(new Dimension(400, 100));
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 12));

        initComponents();
        loadBackgroundImage();


    }


    // ===================== INIT METHODS =====================

    private void initComponents()
    {
        m_energyField = new JTextField(3);
        m_positionFieldX = new JTextField(3);
        m_positionFieldY = new JTextField(3);

        m_entityTypeComboBox = new JComboBox<>(AbstractEntity.EntityType.values());

        m_addButton = new JButton("Add Entity");
        m_addButton.addActionListener(this::onClickSpawnEntity);

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
            m_backgroundImage = ImageIO.read(new File("src/Assets/Background/WoodPanel.png"));
        }
        catch(IOException e)
        {
            System.out.println("Failed to load wood background.");
            e.printStackTrace();
        }
    }
}