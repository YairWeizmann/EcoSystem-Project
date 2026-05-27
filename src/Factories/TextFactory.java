package Factories;

import javax.swing.*;
import java.awt.*;

public class TextFactory
{
    private TextFactory()
    {
        //Preventing from making objects
    }


    //Creation Methods

    public static JLabel createStylizedLabel(String text, Color color , int fontStyle , int fontSize)
    {
        JLabel newLabel = new JLabel(text);
        newLabel.setForeground(color);
        newLabel.setFont(newLabel.getFont().deriveFont(fontStyle,fontSize));

        return newLabel;
    }

    public static JLabel createBasicLabel(String text, Color color)
    {
        JLabel newLabel = new JLabel(text);
        newLabel.setForeground(color);
        return newLabel;
    }


}
