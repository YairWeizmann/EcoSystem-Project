package ecosystem.entities.animals;

import ecosystem.core.Environment;
import ecosystem.entities.AbstractEntity;
import ecosystem.interfaces.*;

import java.util.List;

public class Animal implements Movable , Eater , EdibleByCarnivore, Sensory
{


    //Interface Methods
    //Moveable
    @Override
    public boolean move(Environment env) //Move
    {
        return true;
    }

    //Eater
    @Override
    public boolean eat(Consumable target) //eat a Consumable
    {
     System.out.println("Eating");
     return true;
    }

    //Sensory
    @Override
    public List<AbstractEntity> sense(Environment env) // get all Entities in Distance
    {
      return null;
    }
}
