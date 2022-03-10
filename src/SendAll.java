import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SendAll implements Runnable{

    private static List<Socket> sockets;

    public SendAll(List<Socket> sockets) {
        this.sockets = sockets;
    }

    @Override
    public void run() {
        while (true) {
            if (Data.isNewText()) {
                for (Socket socket : sockets) {
                    try {
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        out.println(Data.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Data.setNewText(false);
            }
        }
    }
}
