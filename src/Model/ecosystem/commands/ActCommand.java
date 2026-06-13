package Model.ecosystem.commands;

import Model.ecosystem.core.Environment;
import Model.ecosystem.interfaces.Actable;
import Model.ecosystem.interfaces.EcosystemCommand;

public class ActCommand implements EcosystemCommand
{
    private Actable m_actable;

    public ActCommand(Actable actableEntity)
    {
        this.m_actable = actableEntity;
    }

    @Override
    public void execute(Environment env)
    {
        Actable activeActable = env.resolveActable(m_actable);
        activeActable.act(env);
    }
}
