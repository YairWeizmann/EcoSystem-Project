import Model.ecosystem.core.Environment;
import Model.ecosystem.interfaces.EcosystemCommand;
import Model.ecosystem.network.NetworkManager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Small manual test for the networking receive-side spawn flow.
 */
public class TestNetwork
{
    public static void main(String[] args) throws InterruptedException
    {
        if (args.length > 0 && args[0].equalsIgnoreCase("client"))
        {
            NetworkManager client = new NetworkManager();
            client.sendMessage("127.0.0.1", "SPAWN,Lion,45,10,5");
            return;
        }

        Environment environment = new Environment();
        BlockingQueue<EcosystemCommand> commandQueue = new LinkedBlockingQueue<>();

        NetworkManager server = new NetworkManager(environment, commandQueue);
        server.setOnCommandExecuted(() -> System.out.println("Network callback called. GUI would repaint now."));
        server.startServer();

        Thread.sleep(500);

        NetworkManager client = new NetworkManager();
        client.sendMessage("127.0.0.1", "SPAWN,Lion,45,10,5");

        Thread.sleep(1000);

        System.out.println("Entities in environment: " + environment.getM_entities().size());
        environment.printMap();

        server.stopServer();
        System.exit(0);
    }
}
