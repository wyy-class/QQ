
import model.Message;
import model.Translate;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class RegisterGUI extends JFrame {

    private JPanel contentPane;
    private JTextField username;
    private JTextField password;
    private JTextField email;
    private JButton registerButton;
    private JButton cancalButton;
    private JFrame jf;

    /**
     * Create the frame.
     */
    public RegisterGUI(String host,int port) {
        jf=this;
        init();
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String usernameValue=username.getText();
                    String passwordValue= password.getText();
                    String emailValue=email.getText();

                    Message message = new Message();
                    message.setUsername(usernameValue);
                    message.setPassword(passwordValue);
                    message.setEmail(emailValue);
                    message.setType("register");
                    Socket socket = new Socket(host, port);
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(Translate.ObjectToByte(message));

                    InputStream inputStream = socket.getInputStream();
                    byte[] bytes = new byte[1024];
                    int len = inputStream.read(bytes);
                    String response = new String(bytes, 0, len);
                    if(response.equals("success")){

                        JOptionPane.showMessageDialog(null,"注册成功",
                                "提示",JOptionPane.WARNING_MESSAGE);
                        jf.setVisible(false);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"账户已存在",
                                "提示",JOptionPane.WARNING_MESSAGE);
                    }
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        cancalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.setVisible(false);
            }
        });

    }

    public void init(){
        setBounds(100, 100, 370, 386);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

         username = new JTextField();
        username.setBounds(49, 63, 199, 24);
        contentPane.add(username);
        username.setColumns(10);

        JLabel lblNewLabel = new JLabel("\u7528\u6237\u6CE8\u518C");
        lblNewLabel.setBounds(113, 13, 72, 18);
        contentPane.add(lblNewLabel);

        JLabel label = new JLabel("\u8D26\u6237");
        label.setBounds(49, 44, 72, 18);
        contentPane.add(label);

         password = new JTextField();
        password.setBounds(49, 124, 199, 24);
        contentPane.add(password);
        password.setColumns(10);

        JLabel label_1 = new JLabel("\u5BC6\u7801");
        label_1.setBounds(49, 105, 72, 18);
        contentPane.add(label_1);

        JLabel label_2 = new JLabel("你叫什么？(私人问题，请勿告诉他人！)");
        label_2.setBounds(49, 161, 72, 18);
        contentPane.add(label_2);

         email = new JTextField();
        email.setBounds(46, 181, 202, 24);
        contentPane.add(email);
        email.setColumns(10);

        registerButton = new JButton("\u6CE8\u518C");
        registerButton.setBounds(36, 243, 113, 27);
        contentPane.add(registerButton);

        cancalButton = new JButton("\u53D6\u6D88");
        cancalButton.setBounds(163, 243, 113, 27);
        contentPane.add(cancalButton);
    }
}

