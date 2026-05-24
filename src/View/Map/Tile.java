package View.Map;

import java.awt.image.BufferedImage;

public class Tile
{
    // ===================== FIELDS =====================
    private BufferedImage m_image;

    // ===================== CONSTRUCTORS =====================
    public Tile(BufferedImage image)
    {
        this.m_image = image;
    }

    // ===================== GETTERS =====================
    public BufferedImage getImage()
    {
        return m_image;
    }
}