import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientReader implements Runnable{

    private static Socket socket;

    public ClientReader(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        try
        {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            System.out.println("IO failed: " + e.getMessage());
            System.exit(-1);
        }
        while(true)
        {
            try
            {
                Data.setText(in.readLine()); // + (char)10;
                Data.setNewText(true);

            }catch (IOException e) {
                System.out.println("Read failed");
                System.exit(-1);
            }
        }
    }
}
