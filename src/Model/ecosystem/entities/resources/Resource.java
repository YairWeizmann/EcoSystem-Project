package Model.ecosystem.entities.resources;

import Model.ecosystem.core.Position;
import Model.ecosystem.entities.StaticEntity;

public abstract class Resource extends StaticEntity
{

    // ===================== Constructors =====================
    public Resource(Position pos ,char symbol ,EntityType entityType)
    {
        super(pos,symbol , entityType);
    }


}
