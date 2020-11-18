
import model.Message;
import model.Translate;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class LoginGUI extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JPasswordField passwordField;
    private JTextField host=null;
    private JTextField port;
    private static LoginGUI frame;
    private JButton registerButton;
    private JButton findButton;
    private JButton loginButton;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new LoginGUI();
                    frame.setLocationRelativeTo(null);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setResizable(false);
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
    public LoginGUI() {
      init();
      //找回密码
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String hostValue = host.getText();
                int portValue = Integer.parseInt(port.getText());
                FindPasswordGUI findPasswordGUI = new FindPasswordGUI(hostValue,portValue);
                findPasswordGUI.setVisible(true);
                findPasswordGUI.setTitle("找回密码");
                findPasswordGUI.setDefaultCloseOperation(HIDE_ON_CLOSE);

            }
        });
        //注册
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String hostValue = host.getText();
                int portValue = Integer.parseInt(port.getText());
                RegisterGUI registerGUI = new RegisterGUI(hostValue,portValue);
                registerGUI.setVisible(true);
                registerGUI.setTitle("账户注册");
                registerGUI.setDefaultCloseOperation(HIDE_ON_CLOSE);
            }
        });
        //登录
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String hostValue = host.getText();
                int portValue = Integer.parseInt(port.getText());
                if (username == null || username.equals("") || password == null || password.equals("")) {
                    JOptionPane.showMessageDialog(null, "账号或密码不能为空",
                            "错误提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Message message = new Message();
                message.setUsername(username);
                message.setPassword(password);
                message.setType("login");
                byte[] messageByte = Translate.ObjectToByte(message);
                Socket socket = new Socket();
                InputStream inputStream = null;
                OutputStream outputStream = null;
                try {
                    socket.connect(new InetSocketAddress(hostValue, portValue));

                    outputStream = socket.getOutputStream();
                    outputStream.write(messageByte, 0, messageByte.length);

                    inputStream = socket.getInputStream();
                    String response = "";
                    byte[] bytes = new byte[1024];
                    int len = inputStream.read(bytes);
                    Message message1 = (Message) Translate.ByteToObject(bytes, 0, len);
                    response = message1.getText();
                    System.out.println(message1.toString());

                    if (response.equals("success")) {
                        frame.dispose();
                        ClientGUI qqClientGUI = new ClientGUI(socket,message1);
                        qqClientGUI.setTitle(message.getUsername());
                        qqClientGUI.setVisible(true);
                        qqClientGUI.setLocationRelativeTo(null);
                        qqClientGUI.setResizable(false);
                    } else if (response.equals("error")) {
                        JOptionPane.showMessageDialog(null, "登录失败，账号或密码错误。", "登录失败", JOptionPane.ERROR_MESSAGE);
                    } else if (response.equals("exist")) {
                        JOptionPane.showMessageDialog(null, "账户已存在", "登录失败", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                }
        });
    }

    public void init(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 598, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //LOGO图像
        JLabel lblNewLabel = new JLabel();
        lblNewLabel.setIcon(new ImageIcon(LoginGUI.class.getResource("image/image.jpg")));
        lblNewLabel.setBounds(3, 34, 140, 235);
        contentPane.add(lblNewLabel);
        //账号

        textField = new JTextField();
        textField.setBounds(152, 68, 220, 44);
        contentPane.add(textField);
        textField.setColumns(10);
        //密码
        passwordField = new JPasswordField();
        passwordField.setBounds(152, 147, 220, 44);
        contentPane.add(passwordField);
        //注册
        registerButton = new JButton("\u6CE8\u518C\u8D26\u53F7");

        registerButton.setBounds(414, 68, 113, 43);
        contentPane.add(registerButton);
        //找回
        findButton = new JButton("\u627E\u56DE\u5BC6\u7801");
        findButton.setBounds(414, 147, 113, 44);
        contentPane.add(findButton);
        //记住密码
        JCheckBox checkBox = new JCheckBox("\u8BB0\u4F4F\u5BC6\u7801");
        checkBox.setBounds(152, 212, 133, 27);
        contentPane.add(checkBox);
        //自动登录
        JCheckBox checkBox_1 = new JCheckBox("\u81EA\u52A8\u767B\u5F55");
        checkBox_1.setBounds(291, 212, 133, 27);
        contentPane.add(checkBox_1);
        //登录
        loginButton = new JButton("\u767B\u5F55");
        loginButton.setBounds(152, 248, 220, 38);
        contentPane.add(loginButton);

        JLabel lblNewLabel_1 = new JLabel("\u670D\u52A1\u5668\u4E3B\u673A\u540D\uFF1A");
        lblNewLabel_1.setBounds(14, 325, 113, 18);
        contentPane.add(lblNewLabel_1);
        //主机号
        host = new JTextField();
        host.setBounds(117, 321, 127, 24);
        host.setText("localhost");
        contentPane.add(host);
        host.setColumns(10);

        JLabel label = new JLabel("\u7AEF\u53E3\uFF1A");
        label.setBounds(425, 323, 72, 18);
        contentPane.add(label);
        //端口
        port = new JTextField();
        port.setBounds(474, 318, 86, 24);
        port.setText("8080");
        contentPane.add(port);
        port.setColumns(10);

        JLabel label_1 = new JLabel("\u8D26\u53F7");
        label_1.setBounds(157, 50, 72, 18);
        contentPane.add(label_1);

        JLabel label_2 = new JLabel("\u5BC6\u7801");
        label_2.setBounds(156, 127, 72, 18);
        contentPane.add(label_2);
    }
}