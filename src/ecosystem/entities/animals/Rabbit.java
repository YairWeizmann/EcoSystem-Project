package ecosystem.entities.animals;

import ecosystem.core.Environment;
import ecosystem.interfaces.Reproducible;

public class Rabbit implements Reproducible
{

    //Interface Methods
    //Reproducible
    @Override
    public boolean reproduce(Environment env)
    {
      System.out.println("Reproduced!!!");
      return true;
    }

}
