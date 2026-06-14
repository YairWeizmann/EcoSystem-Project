package Model.ecosystem.network;

import Factories.EntityFactory;
import Model.ecosystem.core.Environment;
import Model.ecosystem.interfaces.EcosystemCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;

public class NetworkManager
{
    public static final int DEFAULT_PORT = 12345;

    private int m_port;
    private ServerSocket m_serverSocket;
    private Thread m_serverThread;
    private volatile boolean m_running;
    private NetworkMessageParser m_parser;
    private Environment m_environment;
    private EntityFactory m_entityFactory;
    private Runnable m_onCommandExecuted;

    public NetworkManager()
    {
        this(DEFAULT_PORT);
    }

    public NetworkManager(int port)
    {
        this(port, null, null);
    }

    public NetworkManager(Environment environment, BlockingQueue<EcosystemCommand> commandQueue)
    {
        this(DEFAULT_PORT, environment, commandQueue);
    }

    public NetworkManager(int port, Environment environment, BlockingQueue<EcosystemCommand> commandQueue)
    {
        this.m_port = port;
        this.m_parser = new NetworkMessageParser();
        this.m_environment = environment;

        if (commandQueue != null)
        {
            this.m_entityFactory = new EntityFactory(commandQueue);
        }
    }

    public void startServer()
    {
        if (m_running)
        {
            System.out.println("Network server is already running on port " + m_port);
            return;
        }

        // The server runs on another thread so the GUI will not freeze.
        m_running = true;
        m_serverThread = new Thread(this::listenForMessages);
        m_serverThread.setName("NetworkManager-Server");
        m_serverThread.start();
    }

    private void listenForMessages()
    {
        try
        {
            m_serverSocket = new ServerSocket(m_port);
            System.out.println("Network server started on port " + m_port);

            while (m_running)
            {
                try
                {
                    Socket clientSocket = m_serverSocket.accept();
                    receiveMessage(clientSocket);
                }
                catch (IOException e)
                {
                    if (m_running)
                    {
                        System.out.println("Error while accepting network connection.");
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Could not start network server on port " + m_port);
            e.printStackTrace();
        }
        finally
        {
            closeServerSocket();
            m_running = false;
            System.out.println("Network server stopped.");
        }
    }

    private void receiveMessage(Socket clientSocket)
    {
        try (Socket socket = clientSocket;
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)))
        {
            String message = reader.readLine();

            if (message != null)
            {
                System.out.println("Received network message: " + message);

                NetworkCommand command = m_parser.parse(message);

                if (command != null)
                {
                    command.printCommandInfo();

                    if (m_environment != null && m_entityFactory != null)
                    {
                        boolean executed = command.execute(m_environment, m_entityFactory);

                        if (executed)
                        {
                            notifyCommandExecuted();
                        }
                    }
                    else
                    {
                        System.out.println("Network command parsed only. No environment connected.");
                    }
                }
            }
            else
            {
                System.out.println("Received empty network message.");
            }
        }
        catch (IOException e)
        {
            System.out.println("Error while reading network message.");
            e.printStackTrace();
        }
    }

    public boolean sendMessage(String targetIp, String message)
    {
        return sendMessage(targetIp, DEFAULT_PORT, message);
    }

    public boolean sendMessage(String targetIp, int port, String message)
    {
        if (targetIp == null || targetIp.isBlank())
        {
            System.out.println("Cannot send message: target IP is empty.");
            return false;
        }

        if (message == null)
        {
            System.out.println("Cannot send message: message is null.");
            return false;
        }

        try (Socket socket = new Socket(targetIp, port);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8))
        {
            writer.println(message);
            System.out.println("Sent network message to " + targetIp + ":" + port + " -> " + message);
            return true;
        }
        catch (IOException e)
        {
            System.out.println("Could not send network message to " + targetIp + ":" + port);
            e.printStackTrace();
            return false;
        }
    }

    public void stopServer()
    {
        m_running = false;
        closeServerSocket();

        if (m_serverThread != null)
        {
            m_serverThread.interrupt();
        }
    }

    private void closeServerSocket()
    {
        if (m_serverSocket == null || m_serverSocket.isClosed())
            return;

        try
        {
            m_serverSocket.close();
        }
        catch (IOException e)
        {
            System.out.println("Error while closing network server socket.");
            e.printStackTrace();
        }
    }

    public void setOnCommandExecuted(Runnable onCommandExecuted)
    {
        this.m_onCommandExecuted = onCommandExecuted;
    }

    private void notifyCommandExecuted()
    {
        if (m_onCommandExecuted != null)
        {
            m_onCommandExecuted.run();
        }
    }

    public int getM_port()
    {
        return m_port;
    }

    public boolean isRunning()
    {
        return m_running;
    }
}
