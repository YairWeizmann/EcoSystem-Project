package View.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TileManager
{
    // ===================== FIELDS =====================
    private MapPanel m_mapPanel;

    private BufferedImage m_grassTile;
    protected BufferedImage m_groundTile;

    // ===================== CONSTRUCTORS =====================
    public TileManager(MapPanel mapPanel)
    {
        this.m_mapPanel = mapPanel;
    }


    // ===================== INIT METHODS =====================
    public void InitTileImage()
    {
        m_grassTile = loadImage("src/Assets/MapTiles/GrassTile.png");
        m_groundTile = loadImage("src/Assets/MapTiles/GroundTile.png");

    }


    // ===================== DRAW METHODS =====================
    public void drawMap(Graphics graphics)
    {
        int tileSize = m_mapPanel.getM_tileSize();
        int rows = m_mapPanel.getM_rows();
        int cols = m_mapPanel.getM_cols();

        for(int row = 0; row < rows; row++)
        {
            for(int col = 0; col < cols; col++)
            {
                int drawX = col * tileSize;
                int drawY = row * tileSize;

                graphics.drawImage(m_grassTile, drawX, drawY, tileSize, tileSize, null);
            }
        }
    }


    // ===================== IMAGE METHODS =====================
    private BufferedImage loadImage(String path)
    {
        try
        {
            return ImageIO.read(new File(path));
        }
        catch(IOException e)
        {
            System.out.println("Failed to load tile image: " + path);
            e.printStackTrace();
            return null;
        }
    }
}