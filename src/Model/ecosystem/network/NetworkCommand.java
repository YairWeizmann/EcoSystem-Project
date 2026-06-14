package Model.ecosystem.network;

import Factories.EntityFactory;
import Model.ecosystem.core.Environment;

public interface NetworkCommand
{
    boolean execute(Environment environment, EntityFactory entityFactory);
    void printCommandInfo();
}
