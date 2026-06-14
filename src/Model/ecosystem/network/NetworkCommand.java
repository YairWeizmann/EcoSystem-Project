package Model.ecosystem.network;

/**
 * Represents an action received from the network.
 * The command is parsed now and will be executed in a later task.
 */
public interface NetworkCommand
{
    void printCommandInfo();
}
