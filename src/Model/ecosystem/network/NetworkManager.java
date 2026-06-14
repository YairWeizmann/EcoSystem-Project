package Model.ecosystem.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Handles basic network communication for the portal feature.
 * For now it only sends and receives text messages and prints them to the console.
 */
public class NetworkManager
{
    public static final int DEFAULT_PORT = 12345;

    private int m_port;
    private ServerSocket m_serverSocket;
    private Thread m_serverThread;
    private volatile boolean m_running;

    /**
     * Creates a network manager that listens on the default port.
     */
    public NetworkManager()
    {
        this(DEFAULT_PORT);
    }

    /**
     * Creates a network manager that listens on a specific port.
     *
     * @param port the port used by the local server
     */
    public NetworkManager(int port)
    {
        this.m_port = port;
    }

    /**
     * Starts the server listener in a background thread.
     */
    public void startServer()
    {
        if (m_running)
        {
            System.out.println("Network server is already running on port " + m_port);
            return;
        }

        m_running = true;
        m_serverThread = new Thread(this::listenForMessages);
        m_serverThread.setName("NetworkManager-Server");
        m_serverThread.start();
    }

    /**
     * Main server loop. It waits for incoming socket connections and reads one text message.
     */
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

    /**
     * Reads and prints one text message from an accepted client connection.
     *
     * @param clientSocket socket connected to the sender
     */
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

    /**
     * Sends a text message to another computer using the default port.
     *
     * @param targetIp target computer IP address
     * @param message text message to send
     * @return true if the message was sent, false otherwise
     */
    public boolean sendMessage(String targetIp, String message)
    {
        return sendMessage(targetIp, DEFAULT_PORT, message);
    }

    /**
     * Sends a text message to another computer using a specific port.
     *
     * @param targetIp target computer IP address
     * @param port target server port
     * @param message text message to send
     * @return true if the message was sent, false otherwise
     */
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

    /**
     * Stops the server and releases the port.
     */
    public void stopServer()
    {
        m_running = false;
        closeServerSocket();

        if (m_serverThread != null)
        {
            m_serverThread.interrupt();
        }
    }

    /**
     * Closes the server socket safely.
     */
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

    public int getM_port()
    {
        return m_port;
    }

    public boolean isRunning()
    {
        return m_running;
    }
}
