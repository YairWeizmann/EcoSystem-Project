package Model.ecosystem.network;

public class TestNetwork
{
    public static void main(String[] args)
    {
        NetworkManager server = new NetworkManager();
        server.startServer();

        NetworkManager client = new NetworkManager();
        client.sendMessage("127.0.0.1", "SPAWN,Lion,45,10,12");

        server.stopServer();
    }
}
