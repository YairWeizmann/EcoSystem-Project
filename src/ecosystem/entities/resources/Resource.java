package ecosystem.entities.resources;

import ecosystem.core.Position;
import ecosystem.entities.StaticEntity;

public abstract class Resource extends StaticEntity
{

    // ===================== Constructors =====================
    public Resource(Position pos ,char symbol)
    {
        super(pos,symbol);
    }


}
