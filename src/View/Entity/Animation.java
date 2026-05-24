package View.Entity;

import java.awt.image.BufferedImage;

public class Animation
{
    // ===================== FIELDS =====================
    private BufferedImage[] m_frames;
    private int m_currentFrameIndex;
    private long m_lastFrameTime;
    private long m_frameDelay;

    // ===================== CONSTRUCTORS =====================
    public Animation(BufferedImage[] frames, long frameDelay)
    {
        this.m_frames = frames;
        this.m_frameDelay = frameDelay;
        this.m_currentFrameIndex = 0;
        this.m_lastFrameTime = System.currentTimeMillis();
    }

    // ===================== METHODS =====================
    public void update()
    {
        if(m_frames == null || m_frames.length == 0)
            return;

        long currentTime = System.currentTimeMillis();

        if(currentTime - m_lastFrameTime >= m_frameDelay)
        {
            m_currentFrameIndex++;

            if(m_currentFrameIndex >= m_frames.length)
            {
                m_currentFrameIndex = 0;
            }

            m_lastFrameTime = currentTime;
        }
    }

    public BufferedImage getCurrentFrame()
    {
        if(m_frames == null || m_frames.length == 0)
            return null;

        return m_frames[m_currentFrameIndex];
    }
}