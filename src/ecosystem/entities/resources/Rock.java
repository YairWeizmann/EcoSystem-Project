package ecosystem.entities.resources;

import ecosystem.core.Position;

public class Rock extends Resource
{
    //Fields
    boolean blocksMovement = true;

    //Constructor
    public Rock(Position pos)
    {
        super(pos,'X');
    }
}
