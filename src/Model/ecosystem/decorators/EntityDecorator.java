package Model.ecosystem.decorators;

import Model.ecosystem.core.Environment;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.LivingEntity;
import Model.ecosystem.interfaces.Actable;

public abstract class EntityDecorator extends AbstractEntity implements Actable
{
    private Actable m_decoratedEntity;
    private int m_ticksLeft = 10;

    // ============ Constructors ============

    public EntityDecorator(Actable decoratedEntity)
    {
        super(((AbstractEntity) decoratedEntity).getM_position(),((AbstractEntity) decoratedEntity).getM_symbol(), ((AbstractEntity) decoratedEntity).getIs_alive(), ((AbstractEntity) decoratedEntity).getM_entityType());

        this.m_decoratedEntity = decoratedEntity;
    }
    // ============ Getters / Setters============

    public Actable getDecoratedEntity()
    {
        return m_decoratedEntity;
    }

    public int getM_ticksLeft() {
        return m_ticksLeft;
    }

    public void setM_ticksLeft(int m_ticksLeft) {
        this.m_ticksLeft = m_ticksLeft;
    }

    public Actable getM_decoratedEntity() {
        return m_decoratedEntity;
    }

    public void setM_decoratedEntity(Actable m_decoratedEntity) {
        this.m_decoratedEntity = m_decoratedEntity;
    }
    // ============ Helper Methods ============

    protected void decreaseDuration(Environment env)
    {
        m_ticksLeft--;

        if (m_ticksLeft <= 0)
        {
            env.replaceEntity(this, (AbstractEntity) m_decoratedEntity);
        }
    }

    public boolean wraps(Actable target)
    {
        if (m_decoratedEntity == target)
            return true;

        if (m_decoratedEntity instanceof EntityDecorator decorator)
            return decorator.wraps(target);

        return false;
    }


}