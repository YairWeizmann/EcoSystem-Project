package View.Map;

import java.awt.image.BufferedImage;

public class Tile
{
    // ===================== FIELDS =====================

    // The image that represents this tile visually
    private BufferedImage m_image;


    // ===================== CONSTRUCTORS =====================

    public Tile(BufferedImage image)
    {
        // Save the tile image so it can be drawn later
        this.m_image = image;
    }


    // ===================== GETTERS =====================

    public BufferedImage getImage()
    {
        // Return the image of this tile
        return m_image;
    }
}