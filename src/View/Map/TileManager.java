package View.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TileManager
{
    // ===================== FIELDS =====================

    // Reference to the map panel, used to get tile size, rows, and cols
    private MapPanel m_mapPanel;

    // Tile images used when drawing the map
    private BufferedImage m_grassTile;
    protected BufferedImage m_groundTile;


    // ===================== CONSTRUCTORS =====================

    public TileManager(MapPanel mapPanel)
    {
        // Save the map panel so the tile manager knows how big the map should be
        this.m_mapPanel = mapPanel;
    }


    // ===================== INIT METHODS =====================

    public void InitTileImage()
    {
        // Load the grass tile image
        m_grassTile = loadImage("src/Assets/MapTiles/GrassTile.png");

        // Load the ground tile image
        m_groundTile = loadImage("src/Assets/MapTiles/GroundTile.png");
    }


    // ===================== DRAW METHODS =====================

    public void drawMap(Graphics graphics)
    {
        // Get map drawing data from the map panel
        int tileSize = m_mapPanel.getM_tileSize();
        int rows = m_mapPanel.getM_rows();
        int cols = m_mapPanel.getM_cols();

        // Go over every row and column in the map
        for(int row = 0; row < rows; row++)
        {
            for(int col = 0; col < cols; col++)
            {
                // Convert tile position to pixel position
                int drawX = col * tileSize;
                int drawY = row * tileSize;

                // Draw grass tile for now on every tile in the map
                graphics.drawImage(m_grassTile, drawX, drawY, tileSize, tileSize, null);
            }
        }
    }


    // ===================== IMAGE METHODS =====================

    private BufferedImage loadImage(String path)
    {
        try
        {
            // Try to load the image from the given path
            return ImageIO.read(new File(path));
        }
        catch(IOException e)
        {
            // Print an error if the tile image could not be loaded
            System.out.println("Failed to load tile image: " + path);
            e.printStackTrace();
            return null;
        }
    }
}