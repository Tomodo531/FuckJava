import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerMain {
    public static void main(String[] args)
    {
        System.out.println("Server Started!");

        List<Socket> sockets = new ArrayList<Socket>();
        SendAll sendAll = new SendAll(sockets);
        Thread sendAllThread = new Thread(sendAll);
        sendAllThread.start();

        try (ServerSocket serverSocket = new ServerSocket(2000))
        {
            while(true)
            {
                Socket socket = serverSocket.accept();

                ClientReader reader = new ClientReader(socket);
                Thread readerThread = new Thread(reader);
                readerThread.start();

                sockets.add(socket);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Server Ended!");
    }
}
