package View.Entity;

import java.awt.image.BufferedImage;

public class Animation
{
    // ===================== FIELDS =====================

    // All the frames that belong to this animation
    private BufferedImage[] m_frames;

    // The index of the frame that is currently being shown
    private int m_currentFrameIndex;

    // Saves the last time the frame was changed
    private long m_lastFrameTime;

    // How much time should pass before moving to the next frame
    private long m_frameDelay;


    // ===================== CONSTRUCTORS =====================

    public Animation(BufferedImage[] frames, long frameDelay)
    {
        // Store the animation frames
        this.m_frames = frames;

        // Store the delay between each frame
        this.m_frameDelay = frameDelay;

        // Start the animation from the first frame
        this.m_currentFrameIndex = 0;

        // Save the current time so we can compare it later in update()
        this.m_lastFrameTime = System.currentTimeMillis();
    }


    // ===================== METHODS =====================

    public void update()
    {
        // If there are no frames, there is nothing to animate
        if(m_frames == null || m_frames.length == 0)
            return;

        // Get the current time
        long currentTime = System.currentTimeMillis();

        // Check if enough time passed since the last frame change
        if(currentTime - m_lastFrameTime >= m_frameDelay)
        {
            // Move to the next frame
            m_currentFrameIndex++;

            // If we reached the end of the animation, go back to the first frame
            if(m_currentFrameIndex >= m_frames.length)
            {
                m_currentFrameIndex = 0;
            }

            // Update the last frame time to the current time
            m_lastFrameTime = currentTime;
        }
    }

    public BufferedImage getCurrentFrame()
    {
        // If the animation has no frames, return nothing
        if(m_frames == null || m_frames.length == 0)
            return null;

        // Return the frame that should currently be drawn
        return m_frames[m_currentFrameIndex];
    }
}