
import model.Message;
import model.Translate;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FindPasswordGUI extends JFrame {

    private JPanel contentPane;
    private JTextField username;
    private JTextField info;
    private JTextField newPassword;
    private JButton sendButton;
    private JFrame jf;

    /**
     * Create the frame.
     * @param hostValue
     * @param portValue
     */
    public FindPasswordGUI(String hostValue, int portValue) {
        jf=this;
         init();
     sendButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             String usernameText = username.getText();
             String newPasswordText = newPassword.getText();
             String infoText = info.getText();

             Message message = new Message();
             message.setType("findPassword");
             message.setUsername(usernameText);
             message.setPassword(newPasswordText);
             message.setEmail(infoText);
             System.out.println(message.toString());

             try {
                 Socket socket = new Socket(hostValue, portValue);
                 OutputStream outputStream = socket.getOutputStream();
                 outputStream.write(Translate.ObjectToByte(message));

                 InputStream inputStream = socket.getInputStream();
                 byte[] bytes = new byte[1024];
                 int len = inputStream.read(bytes);
                 String response = new String(bytes, 0, len);
                 if(response.equals("success")){

                     JOptionPane.showMessageDialog(null,"密码修改成功",
                             "提示",JOptionPane.WARNING_MESSAGE);
                     jf.setVisible(false);
                 }
                 else{
                     JOptionPane.showMessageDialog(null,"验证信息不正确",
                             "提示",JOptionPane.WARNING_MESSAGE);
                 }
                 socket.close();
             } catch (IOException ioException) {
                 ioException.printStackTrace();
             }
         }
     });
    }
    public void init(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 365);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("\u627E\u56DE\u5BC6\u7801");
        label.setFont(new Font("宋体", Font.PLAIN, 20));
        label.setBounds(153, 13, 102, 18);
        contentPane.add(label);

        username = new JTextField();
        username.setBounds(97, 81, 204, 24);
        contentPane.add(username);
        username.setColumns(10);

        JLabel lblUsername = new JLabel("\u7528\u6237\u540D");
        lblUsername.setBounds(97, 61, 72, 18);
        contentPane.add(lblUsername);

        info = new JTextField();
        info.setBounds(97, 211, 204, 24);
        contentPane.add(info);
        info.setColumns(10);

        JLabel label_1 = new JLabel("\u4F60\u53EB\u4EC0\u4E48\uFF1F");
        label_1.setBounds(97, 191, 95, 18);
        contentPane.add(label_1);

        newPassword = new JTextField();
        newPassword.setBounds(97, 146, 204, 24);
        contentPane.add(newPassword);
        newPassword.setColumns(10);

        JLabel label_2 = new JLabel("\u65B0\u5BC6\u7801");
        label_2.setBounds(97, 128, 72, 18);
        contentPane.add(label_2);

         sendButton = new JButton("\u786E\u5B9A");
        sendButton.setBounds(97, 266, 204, 27);
        contentPane.add(sendButton);
    }
}
