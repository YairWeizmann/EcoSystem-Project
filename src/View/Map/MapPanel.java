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

public class MapPanel extends JPanel implements MouseMotionListener , MouseListener
{
    // ===================== FIELDS =====================

    private Environment m_environment;
    private TileManager m_tileManager;

    //Managers
    private EntityImageManager m_entityImageManager;

    //Panels
    private EntityInfoPanel m_entityInfoPanel;
    private InfoPanel m_InfoPanel;

    //Tiles
    private int m_tileSize = 64;
    private int m_rows;
    private int m_cols;

    private int m_selectedRow;
    private int m_selectedCol;

    //Timers
    private Timer m_animationTimer;


    // ===================== CONSTRUCTORS =====================

    public MapPanel(Environment environment , InfoPanel infoPanel)
    {
        this.m_environment = environment;
        this.m_InfoPanel = infoPanel;

        initComponents();
        startAnimation();

        setPreferredSize(new Dimension(m_environment.getCols() * m_tileSize, m_environment.getRows() * m_tileSize));
    }


    // ===================== INIT METHODS =====================

    private void initComponents()
    {
        m_rows = m_environment.getRows();
        m_cols = m_environment.getCols();

        m_tileManager = new TileManager(this);
        m_tileManager.InitTileImage();

        m_entityImageManager = new EntityImageManager();

        setLayout(null);

        createEntityInfoPanel();

        addMouseMotionListener(this);
        addMouseListener(this);
    }


    // ===================== PAINT METHODS =====================

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        m_tileManager.drawMap(g);

        drawEntities(g);

        drawSelectedTile(g);
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

    private void drawSelectedTile(Graphics g)
    {
        if(m_selectedRow == -1 || m_selectedCol == -1)
            return;

        int x = m_selectedCol * m_tileSize;
        int y = m_selectedRow * m_tileSize;

        g.setColor(Color.YELLOW);
        g.drawRect(x, y, m_tileSize - 1, m_tileSize - 1);

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


    // ===================== Mouse METHODS =====================
    @Override
    public void mouseMoved(MouseEvent event)
    {
        int col = event.getX() / m_tileSize;
        int row = event.getY() / m_tileSize;

        AbstractEntity entity = m_environment.getEntityAt(row, col);

        if(entity != null)
        {
            m_entityInfoPanel.showEntity(entity);
        }
        else
        {
            m_entityInfoPanel.toggleHide();
        }

    }
    @Override
    public void mouseDragged(MouseEvent event)
    {
        // Not needed for now
    }

    @Override
    public void mouseClicked(MouseEvent event)
    {
        m_selectedCol = event.getX() / m_tileSize;
        m_selectedRow = event.getY() / m_tileSize;

        AbstractEntity entity = m_environment.getEntityAt(m_selectedRow , m_selectedCol);

        m_InfoPanel.showInfo(entity);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent event) {}

    @Override
    public void mouseReleased(MouseEvent event) {}

    @Override
    public void mouseEntered(MouseEvent event) {}

    @Override
    public void mouseExited(MouseEvent event) {}


    // ===================== UI METHODS =====================
    private void createEntityInfoPanel()
    {

        m_entityInfoPanel = new EntityInfoPanel();
        m_entityInfoPanel.setBounds(0, 0, 180, 120);
        m_entityInfoPanel.setVisible(false);

        add(m_entityInfoPanel);
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