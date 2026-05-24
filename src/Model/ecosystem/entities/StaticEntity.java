package Model.ecosystem.entities;

import Model.ecosystem.core.Position;

public abstract class StaticEntity extends AbstractEntity
{
    // ===================== Constructors =====================
     public StaticEntity(Position pos,char symbol , EntityType entityType)
     {
         super(pos,symbol,true , entityType);
     }

    // ===================== Getters / Setters =====================



}