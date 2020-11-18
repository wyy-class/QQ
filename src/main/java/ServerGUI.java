
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ServerGUI extends JFrame {

    private JPanel contentPane;
    private JTextField host;
    private JTextField port;
    private JTextArea conten;
    JButton start;
    private static ExecutorService executorService=null;
    static ArrayList<Socket> lists=new ArrayList<Socket>();

    private static Vector<String> users=new Vector<String>();
    static{
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ServerGUI frame = new ServerGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ServerGUI() {
     init();
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(port.getText().equals("")||port.getText()==null){
                    JOptionPane.showMessageDialog(null,"请填写端口号","启动错误",JOptionPane.WARNING_MESSAGE);
                }
                else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ServerSocket serverSocket = new ServerSocket(Integer.parseInt(port.getText()));
                                conten.append("服务器开始监听。。。"+System.lineSeparator());
                                while(true){
                                    Socket socket = serverSocket.accept();
                                    System.out.println(socket.getInetAddress().toString());
                                    executorService.execute(new ServerThread(socket,conten,lists,users));
                                }
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }

                        }
                    }).start();
                    //开启文件传输通道监听
                    new Thread(new SendFileThread(conten)).start();
                }

            }
        });

    }
    public void init(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 599, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("\u542F\u52A8\u670D\u52A1\u5668");
        label.setBounds(14, 13, 102, 26);
        contentPane.add(label);

        JPanel panel = new JPanel();
        panel.setBounds(24, 39, 543, 62);
        panel.setBorder(null);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel label_2 = new JLabel("\u4E3B\u673A\u53F7\uFF1A");
        label_2.setBounds(5, 0, 72, 62);
        panel.add(label_2);

        host = new JTextField();
        host.setBounds(67, 13, 160, 31);
        host.setText("localhost");
        panel.add(host);
        host.setColumns(10);

        JLabel label_3 = new JLabel("\u7AEF\u53E3\uFF1A");
        label_3.setBounds(235, 0, 72, 62);
        panel.add(label_3);

        port = new JTextField();
        port.setBounds(283, 13, 86, 30);
        port.setText("8080");
        panel.add(port);
        port.setColumns(10);

         start = new JButton("\u542F\u52A8");
        start.setBounds(406, 13, 113, 33);
        panel.add(start);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(29, 189, 48, -20);
        contentPane.add(scrollPane);

        JLabel label_1 = new JLabel("\u804A\u5929\u5BA4\u5927\u5385");
        label_1.setBounds(14, 107, 89, 26);
        contentPane.add(label_1);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(39, 225, 240, -34);
        contentPane.add(scrollPane_1);

        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(24, 135, 543, 205);
        contentPane.add(scrollPane_2);

        conten = new JTextArea();
        scrollPane_2.setViewportView(conten);

    }
}
