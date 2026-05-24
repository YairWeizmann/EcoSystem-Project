package Model.ecosystem.interfaces;
import Model.ecosystem.core.Environment;
import Model.ecosystem.entities.AbstractEntity;

import java.util.List;

public interface Sensory
{

    //Methods
    public List<AbstractEntity> sense(Environment env);
}
