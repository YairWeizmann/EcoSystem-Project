package Controller.Map;

import Model.ecosystem.core.Environment;
import Model.ecosystem.entities.AbstractEntity;
import View.Entity.EntityInfoPanel;
import View.Info.InfoPanel;
import View.Map.MapPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MapController implements MouseMotionListener , MouseListener
{
    // ===================== Fields =====================

    // The map panel is the visual part that the user clicks and hovers on
    private MapPanel m_mapPanel;

    // The environment stores all the entities and their positions
    private Environment m_environment;

    // The side/bottom info panel that shows selected entity details
    private InfoPanel m_infoPanel;

    // Small floating info panel that appears when hovering over an entity
    private EntityInfoPanel m_entityInfoPanel;


    // ===================== Constructors =====================

    public MapController(MapPanel mapPanel , Environment environment , InfoPanel infoPanel)
    {
        // Save the references that the controller needs to handle map actions
        this.m_mapPanel = mapPanel;
        this.m_environment = environment;
        this.m_infoPanel = infoPanel;

        // Create the small floating info panel inside the map panel
        this.m_entityInfoPanel = mapPanel.createEntityInfoPanel(this.m_entityInfoPanel);
    }


    // ===================== Mouse METHODS =====================

    @Override
    public void mouseMoved(MouseEvent event)
    {
        // Convert mouse pixel position into map grid position
        int col = event.getX() / m_mapPanel.getM_tileSize();
        int row = event.getY() / m_mapPanel.getM_tileSize();

        // Check if there is an entity on the tile the mouse is currently hovering
        AbstractEntity entity = m_mapPanel.getM_environment().getEntityAt(row, col);

        if(entity != null)
        {
            // If there is an entity, show its quick hover info
            m_entityInfoPanel.showEntity(entity);
        }
        else
        {
            // If there is no entity, hide the hover info panel
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
        // Convert clicked pixel position into map grid position
        int col = event.getX() / m_mapPanel.getM_tileSize();
        int row = event.getY() / m_mapPanel.getM_tileSize();

        // Mark the clicked tile as selected in the map panel
        m_mapPanel.selectTile(row, col);

        // Try to find an entity on the clicked tile
        AbstractEntity entity = m_environment.getEntityAt(row, col);

        // Show the selected entity info in the main info panel
        m_infoPanel.showInfo(entity);
    }

    @Override
    public void mousePressed(MouseEvent event) {}

    @Override
    public void mouseReleased(MouseEvent event) {}

    @Override
    public void mouseEntered(MouseEvent event) {}

    @Override
    public void mouseExited(MouseEvent event) {}

}