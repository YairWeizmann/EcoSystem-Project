package Model.ecosystem.entities.resources;

import Model.ecosystem.core.Position;

public class Rock extends Resource
{
    //Fields
    boolean blocksMovement = true;

    //Constructor
    public Rock(Position pos)
    {
        super(pos,'X' ,EntityType.Rock);
    }
}
