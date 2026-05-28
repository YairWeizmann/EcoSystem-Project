package View.Map;

import Model.ecosystem.core.Environment;
import Model.ecosystem.entities.AbstractEntity;
import View.Entity.EntityImageManager;
import View.Entity.EntityInfoPanel;
import View.Info.InfoPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MapPanel extends JPanel
{
    // ===================== FIELDS =====================

    // The environment holds all the entities that should be drawn on the map
    private Environment m_environment;

    // Responsible for drawing the map tiles
    private TileManager m_tileManager;

    // Manager that knows how to draw entity images and animations
    private EntityImageManager m_entityImageManager;


    // ===================== TILE DATA =====================

    // Size of each tile in pixels
    private int m_tileSize = 64;

    // Amount of rows and columns in the map
    private int m_rows;
    private int m_cols;

    // The currently selected tile position
    // -1 means no tile is selected yet
    private int m_selectedRow;
    private int m_selectedCol;


    // ===================== TIMERS =====================

    // Timer that updates the entity animations
    private Timer m_animationTimer;


    // ===================== CONSTRUCTORS =====================

    public MapPanel(Environment environment)
    {
        // Save the environment so the panel can draw its entities
        this.m_environment = environment;

        // Create all managers and panel settings
        initComponents();

        // Start the animation timer for animated entities
        startAnimation();

        // Set the size of the map based on the amount of rows/cols and tile size
        setPreferredSize(new Dimension(m_environment.getCols() * m_tileSize, m_environment.getRows() * m_tileSize));
    }


    // ===================== INIT METHODS =====================

    private void initComponents()
    {
        // Take the map size from the environment so the view and model stay matched
        m_rows = m_environment.getRows();
        m_cols = m_environment.getCols();

        // Create and initialize the tile manager
        m_tileManager = new TileManager(this);
        m_tileManager.InitTileImage();

        // Create the manager that draws entity sprites/images
        m_entityImageManager = new EntityImageManager();

        // Null layout lets us place floating panels manually on top of the map
        setLayout(null);
    }


    // ===================== PAINT METHODS =====================

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // Draw the map tiles first
        m_tileManager.drawMap(g);

        // Draw all the entities above the tiles
        drawEntities(g);

        // Draw the selected tile border above everything else
        drawSelectedTile(g);
    }


    // ===================== DRAW METHODS =====================

    private void drawEntities(Graphics g)
    {
        // Go over all entities in the environment and draw the alive ones
        for(AbstractEntity entity : m_environment.getM_entities())
        {
            if(entity == null || !entity.getIs_alive())
                continue;

            // Convert grid position to pixel position
            int drawX = entity.getM_position().getCol() * m_tileSize;
            int drawY = entity.getM_position().getRow() * m_tileSize;

            // Let the image manager decide which image to draw for this entity
            m_entityImageManager.drawEntity(g, entity, drawX, drawY, m_tileSize);
        }
    }

    private void drawSelectedTile(Graphics g)
    {
        // If no tile is selected yet, don't draw anything
        if(m_selectedRow == -1 || m_selectedCol == -1)
            return;

        // Convert selected grid position to pixel position
        int x = m_selectedCol * m_tileSize;
        int y = m_selectedRow * m_tileSize;

        // Draw a yellow border around the selected tile
        g.setColor(Color.YELLOW);
        g.drawRect(x, y, m_tileSize - 1, m_tileSize - 1);
    }

    public void selectTile(int row, int col)
    {
        // Save the selected tile position
        this.m_selectedRow = row;
        this.m_selectedCol = col;

        // Redraw the map so the yellow selection border updates
        repaint();
    }


    // ===================== ENTITY METHODS =====================

    public void spawnEntity(AbstractEntity entity)
    {
        // Try adding the entity to the environment
        boolean added = m_environment.addEntity(entity);

        // If the entity was added successfully, redraw the map
        if(added)
        {
            repaint();
        }
    }


    // ===================== UI METHODS =====================

    public EntityInfoPanel createEntityInfoPanel(EntityInfoPanel entityInfoPanel)
    {
        // Create the small floating info panel that appears over the map
        entityInfoPanel = new EntityInfoPanel();

        // Set its position and size manually because the map panel uses null layout
        entityInfoPanel.setBounds(0, 0, 180, 120);

        // Start hidden until the user hovers over an entity
        entityInfoPanel.setVisible(false);

        // Add it on top of the map panel
        add(entityInfoPanel);

        return entityInfoPanel;
    }


    // ===================== ANIMATION METHODS =====================

    private void startAnimation()
    {
        // Update animations every 100 milliseconds and repaint the map
        m_animationTimer = new Timer(100, e -> {
            m_entityImageManager.updateAnimations();
            repaint();
        });

        // Start the animation timer
        m_animationTimer.start();
    }


    // ===================== GETTERS =====================

    public int getM_tileSize()
    {
        return m_tileSize;
    }

    public int getM_rows()
    {
        return m_rows;
    }

    public int getM_cols()
    {
        return m_cols;
    }

    public Environment getM_environment()
    {
        return m_environment;
    }

}