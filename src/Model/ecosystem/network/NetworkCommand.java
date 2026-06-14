package Model.ecosystem.network;

import Factories.EntityFactory;
import Model.ecosystem.core.Environment;

/**
 * Represents an action received from the network.
 */
public interface NetworkCommand
{
    boolean execute(Environment environment, EntityFactory entityFactory);
    void printCommandInfo();
}
