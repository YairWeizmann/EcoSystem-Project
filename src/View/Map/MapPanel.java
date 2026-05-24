package View.Map;

import Model.ecosystem.core.Environment;
import Model.ecosystem.entities.AbstractEntity;
import View.Entity.EntityImageManager;
import View.Map.TileManager  ;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel
{
    // ===================== FIELDS =====================

    private Environment m_environment;
    private TileManager m_tileManager;
    private EntityImageManager m_entityImageManager;

    private int m_tileSize = 64;
    private int m_rows;
    private int m_cols;

    private Timer m_animationTimer;


    // ===================== CONSTRUCTORS =====================

    public MapPanel(Environment environment)
    {
        this.m_environment = environment;

        initComponents();
        startAnimation();

        setPreferredSize(new Dimension(
                m_environment.getCols() * m_tileSize,
                m_environment.getRows() * m_tileSize
        ));
    }


    // ===================== INIT METHODS =====================

    private void initComponents()
    {
        m_rows = m_environment.getRows();
        m_cols = m_environment.getCols();

        m_tileManager = new TileManager(this);
        m_tileManager.InitTileImage();

        m_entityImageManager = new EntityImageManager();
    }


    // ===================== PAINT METHODS =====================

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        m_tileManager.drawMap(g);

        drawEntities(g);
    }


    // ===================== DRAW METHODS =====================

    private void drawEntities(Graphics g)
    {
        for(AbstractEntity entity : m_environment.getM_entities())
        {
            if(entity == null || !entity.getIs_alive())
                continue;

            int drawX = entity.getM_position().getCol() * m_tileSize;
            int drawY = entity.getM_position().getRow() * m_tileSize;

            m_entityImageManager.drawEntity(g, entity, drawX, drawY, m_tileSize);
        }
    }


    // ===================== ENTITY METHODS =====================

    public void spawnEntity(AbstractEntity entity)
    {
        boolean added = m_environment.addEntity(entity);

        if(added)
        {
            repaint();
        }
    }


    // ===================== ANIMATION METHODS =====================

    private void startAnimation()
    {
        m_animationTimer = new Timer(100, e -> {
            m_entityImageManager.updateAnimations();
            repaint();
        });

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