package Model.ecosystem.network;

import Model.ecosystem.entities.AbstractEntity;

/**
 * Converts network text messages into network command objects.
 */
public class NetworkMessageParser
{
    /**
     * Parses a text message like: SPAWN,Lion,45,10,12
     *
     * @param message text received from the network
     * @return matching network command, or null if the message is invalid
     */
    public NetworkCommand parse(String message)
    {
        if (message == null || message.isBlank())
        {
            System.out.println("Cannot parse empty network message.");
            return null;
        }

        String[] parts = message.split(",");

        if (parts.length == 0)
            return null;

        String action = parts[0].trim();

        if (action.equalsIgnoreCase("SPAWN"))
        {
            return parseSpawnCommand(parts);
        }

        System.out.println("Unknown network command: " + action);
        return null;
    }

    /**
     * Parses SPAWN,EntityType,Energy,X,Y into a SpawnEntityCommand.
     */
    private NetworkCommand parseSpawnCommand(String[] parts)
    {
        if (parts.length != 5)
        {
            System.out.println("Invalid SPAWN message. Expected: SPAWN,EntityType,Energy,X,Y");
            return null;
        }

        try
        {
            AbstractEntity.EntityType entityType =
                    AbstractEntity.EntityType.valueOf(parts[1].trim());

            int energy = Integer.parseInt(parts[2].trim());
            int x = Integer.parseInt(parts[3].trim());
            int y = Integer.parseInt(parts[4].trim());

            return new SpawnEntityCommand(entityType, energy, x, y);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Invalid SPAWN message values.");
            e.printStackTrace();
            return null;
        }
    }
}
