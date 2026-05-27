package View.Entity;

import Factories.TextFactory;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.LivingEntity;
import Model.ecosystem.entities.StaticEntity;

import javax.swing.*;
import java.awt.*;

public class EntityInfoPanel extends JPanel
{
    // ===================== FIELDS =====================
    private JLabel m_name;
    private JLabel m_energy;
    private JLabel m_age;
    private JLabel m_isalive;

    // ===================== Constructors =====================
    public EntityInfoPanel()
    {
        InitComponents();



        setLayout(new GridLayout(3, 1));
        setVisible(false);
    }
    // ===================== Helper Methods =====================
    private void InitComponents()
    {
        m_name = TextFactory.createStylizedLabel("Name",Color.white,Font.BOLD,16);
        m_energy = TextFactory.createStylizedLabel("Name",Color.white,Font.BOLD,16);
        m_age = TextFactory.createStylizedLabel("Name",Color.white,Font.BOLD,16);
        m_isalive = TextFactory.createStylizedLabel("Name",Color.white,Font.BOLD,16);

        add(m_name);
        add(m_energy);
        add(m_age);
        add(m_isalive);

        setBackground(new Color(0, 0, 0, 160));
        setOpaque(true);
    }


    public void showEntity(AbstractEntity entity)
    {
        if(entity == null)
        {
            toggleHide();
            return;
        }


        LivingEntity livingEntity = null;
        StaticEntity staticEntity = null;

        if(entity instanceof LivingEntity)
            livingEntity = (LivingEntity)entity;

        if(entity instanceof StaticEntity)
            staticEntity = (StaticEntity) staticEntity;

        if(livingEntity != null)
        {
            m_name.setText("Name:  " + entity.getM_entityType());
            m_energy.setText("Energy:  " + livingEntity.getM_energy());
            m_age.setText("Age:  " + livingEntity.getM_age());
            m_isalive.setText("Is Alive:  " + livingEntity.getIs_alive());
        }

        if(staticEntity != null)
        {
            m_name.setText("Name:  " + entity.getM_entityType());
        }

        setVisible(true);
    }
    public void toggleHide()
    {
        setVisible(false);
    }

}
