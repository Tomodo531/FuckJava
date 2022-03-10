import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import javax.swing.*;


public class Client extends JFrame {
    JLabel lblSend, lblReceived;
    JTextField txtSend;
    JTextArea txtReceived;
    JButton btnSend, btnExit;

    Socket sock;
    BufferedReader in;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.setTitle("Chat client");
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.setBounds(200, 200, 550, 450);
        client.setVisible(true);
    }

    public Client() throws IOException {
        lblSend = new JLabel("Send");
        lblReceived = new JLabel("Received");
        txtSend = new JTextField(20);
        txtSend.setMaximumSize(txtSend.getPreferredSize());//At den ikke fylder hele højden
        txtReceived = new JTextArea(20, 20);
        txtReceived.setLineWrap(true);

        btnSend = new JButton("Send");
        btnExit = new JButton("Exit");
        btnExit.setPreferredSize(btnSend.getPreferredSize());

        JScrollPane sp = new JScrollPane(txtReceived);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel pnl1 = new JPanel();
        pnl1.setLayout(new GridBagLayout());
        GridBagConstraints gs = new GridBagConstraints();
        gs.insets = new Insets(10, 10, 10, 10);
        // Indsætter margin omkring cellen, hvilket påvirker hele rækken og kolonnen.
        // Når den bruges på alle bliver det pænt.
        gs.gridx = 1;
        gs.gridy = 1;
        pnl1.add(lblSend, gs);
        gs.gridx = 1;
        gs.gridy = 2;
        gs.gridwidth = 3;
        pnl1.add(txtSend, gs);
        gs.gridwidth = 1;
        gs.gridx = 1;
        gs.gridy = 3;
        pnl1.add(btnSend, gs);
        gs.gridx = 1;
        gs.gridy = 4;
        pnl1.add(btnExit, gs);

        JPanel pnl2 = new JPanel();

        pnl2.setLayout(new GridBagLayout());
        gs.gridx = 1;
        gs.gridy = 1;
        //Skal rykkes lidt mere til højre fordi teksten er længere end i labelen Send
        gs.insets = new Insets(10, 30, 10, 10);
        pnl2.add(lblReceived, gs);
        gs.gridx = 1;
        gs.gridy = 2;
        gs.gridwidth = 2;
        gs.gridheight = 15;
        gs.insets = new Insets(10, 10, 10, 10);
        pnl2.add(sp, gs);

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        add(pnl1);
        add(pnl2);

        try {
            sock = new Socket("localhost", 2000);
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintWriter(sock.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("Click");
                out.println(txtSend.getText());
            }
        });

        Thread getThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        txtReceived.insert(in.readLine() + "\n", 0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        });

        getThread.start();
    }
}