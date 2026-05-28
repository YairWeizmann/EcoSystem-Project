package View.Entity;

import Model.ecosystem.entities.AbstractEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntityImageManager
{
    // ===================== FIELDS =====================

    // Animal idle animations
    private Animation m_deerIdleAnimation;
    private Animation m_rabbitIdleAnimation;
    private Animation m_lionIdleAnimation;

    // Static plant images
    private BufferedImage m_oakTree;
    private BufferedImage m_flower;

    // Static resource images
    private BufferedImage m_rock;
    private BufferedImage m_water;

    // List that holds all animated animal animations so we can update them together
    private List<Animation> m_animalsAnimations;


    // ===================== CONSTRUCTORS =====================

    public EntityImageManager()
    {
        // Initialize the animation list before adding animations to it
        m_animalsAnimations = new ArrayList<>();

        // Load all animal sprite sheets and create their animations
        loadDeerImages();
        loadRabbitImages();
        loadLionImages();

        // Load all static images that do not need animation
        loadOakTreeImage();
        loadFlowerImage();
        loadWaterImage();
        loadRockImage();
    }


    // ===================== DRAW METHODS =====================

    public void drawEntity(Graphics graphics, AbstractEntity entity, int colX, int rowY, int size)
    {
        // If there is no entity, there is nothing to draw
        if(entity == null)
            return;

        // Get the type of the entity so we know which image to draw
        AbstractEntity.EntityType currentEntityType = entity.getM_entityType();

        // These can be used later if some entity needs to be drawn bigger or smaller
        int heightAdjust = 0;
        int widthAdjust = 0;

        switch(currentEntityType)
        {
            case Deer:

                // Draw the current idle frame of the deer animation
                graphics.drawImage(m_deerIdleAnimation.getCurrentFrame(), colX, rowY, size + widthAdjust , size + heightAdjust, null);
                break;

            case Rabbit:

                // Draw the current idle frame of the rabbit animation
                graphics.drawImage(m_rabbitIdleAnimation.getCurrentFrame(), colX, rowY, size + widthAdjust, size + heightAdjust, null);
                break;

            case Lion:

                // Draw the current idle frame of the lion animation
                graphics.drawImage(m_lionIdleAnimation.getCurrentFrame(), colX, rowY, size + widthAdjust, size + heightAdjust, null);
                break;

            case OakTree:

                // Draw the oak tree as a static image
                graphics.drawImage(m_oakTree, colX, rowY, size + widthAdjust, size + heightAdjust, null);
                break;

            case Flower:

                // Draw the flower as a static image
                graphics.drawImage(m_flower, colX, rowY, size, size, null);
                break;

            case Water:

                // Draw water resource image
                graphics.drawImage(m_water,colX,rowY,size,size,null);
                break;

            case Rock:

                // Draw rock resource image
                graphics.drawImage(m_rock,colX,rowY,size,size,null);
                break;

            default:
                // If the entity type is not handled, draw nothing
                break;
        }
    }


    // ===================== IMAGE HELPER METHODS =====================

    private BufferedImage loadImage(String path)
    {
        try
        {
            // Try loading the image from the given file path
            return ImageIO.read(new File(path));
        }
        catch(IOException e)
        {
            // If the image failed to load, print the path so it is easier to debug
            System.out.println("Failed to load image: " + path);
            e.printStackTrace();
            return null;
        }
    }

    private BufferedImage sliceImage(BufferedImage spriteSheet, int x, int y, int width, int height)
    {
        // Cut one frame from the sprite sheet
        return spriteSheet.getSubimage(x, y, width, height);
    }

    private BufferedImage[] createRowFrames(BufferedImage spriteSheet, int rowY, int frameSize, int frameCount)
    {
        // Create an array that will hold all the animation frames
        BufferedImage[] frames = new BufferedImage[frameCount];

        for(int frameIndex = 0; frameIndex < frameCount; frameIndex++)
        {
            // Slice each frame from the same row in the sprite sheet
            frames[frameIndex] = sliceImage(
                    spriteSheet,
                    frameIndex * frameSize,
                    rowY,
                    frameSize,
                    frameSize
            );
        }

        return frames;
    }


    // ===================== ANIMATION METHODS =====================

    public void updateAnimations()
    {
        // If the animation list was not created for some reason, do nothing
        if(m_animalsAnimations == null)
            return;

        // Update all animal animations together
        for(Animation animation : m_animalsAnimations)
        {
            animation.update();
        }
    }


    // ===================== LOAD ANIMAL IMAGES =====================

    private void loadDeerImages()
    {
        // Load the deer sprite sheet from the assets folder
        BufferedImage deerSpriteSheet = loadImage("src/Assets/Animals/MiniDeer.png");

        // If the image failed to load, stop here
        if(deerSpriteSheet == null)
            return;

        // Deer sprite sheet has 4 frames, each frame is 32x32
        BufferedImage[] deerIdleFrames = createRowFrames(deerSpriteSheet, 0, 32, 4);

        // Create the deer idle animation and add it to the animations list
        m_deerIdleAnimation = new Animation(deerIdleFrames, 150);
        m_animalsAnimations.add(m_deerIdleAnimation);
    }

    private void loadRabbitImages()
    {
        // Load the rabbit sprite sheet from the assets folder
        BufferedImage rabbitSpriteSheet = loadImage("src/Assets/Animals/MiniBunny.png");

        // If the image failed to load, stop here
        if(rabbitSpriteSheet == null)
            return;

        // Rabbit sprite sheet has 4 frames, each frame is 32x32
        BufferedImage[] rabbitIdleFrames = createRowFrames(rabbitSpriteSheet, 0, 32, 4);

        // Create the rabbit idle animation and add it to the animations list
        m_rabbitIdleAnimation = new Animation(rabbitIdleFrames, 150);
        m_animalsAnimations.add(m_rabbitIdleAnimation);
    }

    private void loadLionImages()
    {
        // Load the lion sprite sheet from the assets folder
        BufferedImage lionSpriteSheet = loadImage("src/Assets/Animals/MiniLion.png");

        // If the image failed to load, stop here
        if(lionSpriteSheet == null)
            return;

        // MiniLion.png = 1000x250
        // 4 frames => each frame is 250x250
        BufferedImage[] lionIdleFrames = createRowFrames(lionSpriteSheet, 0, 250, 4);

        // Create the lion idle animation and add it to the animations list
        m_lionIdleAnimation = new Animation(lionIdleFrames, 150);
        m_animalsAnimations.add(m_lionIdleAnimation);
    }


    // ===================== LOAD PLANT IMAGES =====================

    private void loadOakTreeImage()
    {
        // Load the oak tree image
        m_oakTree = loadImage("src/Assets/plants/OakTree.png");
    }

    private void loadFlowerImage()
    {
        // Load the flower image
        m_flower = loadImage("src/Assets/plants/Flower.png");
    }

    // ===================== LOAD Resources IMAGES =====================

    private void  loadWaterImage()
    {
        // Load the water resource image
        m_water = loadImage("src/Assets/Resources/Water.png");
    }

    private void loadRockImage()
    {
        // Load the rock resource image
        m_rock = loadImage("src/Assets/Resources/Rock.png");
    }

}