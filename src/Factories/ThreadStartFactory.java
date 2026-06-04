package Factories;

import Model.ecosystem.entities.AbstractEntity;

public class ThreadStartFactory
{

    public static void StartThread(AbstractEntity entity)
    {
        try
        {
            Thread newThread = new Thread((Runnable) entity);
            newThread.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
