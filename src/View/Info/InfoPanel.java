package View.Info;

import Factories.TextFactory;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.LivingEntity;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel
{
    // ===================== FIELDS =====================
    private JLabel m_name;
    private JLabel m_energy;
    private JLabel m_age;
    private JLabel m_isalive;

    // ===================== Constructors =====================

    public InfoPanel()
    {
        InitComponents();
    }

    // ===================== Helper Methods =====================

    private void InitComponents()
    {
        m_name = TextFactory.createStylizedLabel("Name: ", Color.white,Font.BOLD,16);
        m_energy = TextFactory.createStylizedLabel("Energy: ",Color.white,Font.BOLD,16);
        m_age = TextFactory.createStylizedLabel("Age: ",Color.white,Font.BOLD,16);
        m_isalive = TextFactory.createStylizedLabel("Is Alive:  ",Color.white,Font.BOLD,16);

        add(m_name);
        add(m_energy);
        add(m_age);
        add(m_isalive);


        setPreferredSize(new Dimension(150, 100));
        setLayout(new GridLayout(4, 1)); // 4 rows, 1 column
        setBackground(new Color(0, 0, 0, 100));
        setOpaque(true);
        setVisible(false);
    }


    public void showInfo(AbstractEntity entity)
    {
        LivingEntity livingEntity = null;

        if(entity == null)
        {
            setVisible(false);
            return;
        }
        if(!(entity instanceof LivingEntity))
        {
            setVisible(false);
            return;
        }

        setVisible(true);
        livingEntity = (LivingEntity) entity;

        m_name.setText("Name:  " + entity.getM_entityType());
        m_energy.setText("Energy:  " + livingEntity.getM_energy());
        m_age.setText("Age:  " + livingEntity.getM_age());
        m_isalive.setText("Is Alive:  " + livingEntity.getIs_alive());
    }
}
