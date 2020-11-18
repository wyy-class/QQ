
import model.Message;
import model.Translate;

import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ClientGUI extends JFrame {
    private JPanel contentPane;
    private Socket socket;
    private JTextArea textArea;
    private  JTextArea textArea_1;
    private JList list;
    private JFrame frame=this;
    private Message message;
    private Message  messageInit;
    JButton faceButton;
    JButton sendMsgButton;
    JButton sendFileButton;
    JButton fileSelect;
    boolean isFile=false;

    /**
     * Create the frame.
     *
     */
    public ClientGUI(final Socket socket, final Message message) throws IOException {
       this.socket=socket;
        this.message=message;
        this.messageInit=message;
        isFile=false;
        init();
        textArea.append(message.getUsername()+": 已上线"+System.lineSeparator());
        if(message.getUsers()!=null)
        list.setListData(message.getUsers());
        new Thread(new ClientThread(socket,textArea,list)).start();
        //窗口关闭
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                    message.setType("quit");
                    byte[] bytes = Translate.ObjectToByte(message);
                    try {
                        OutputStream outputStream = socket.getOutputStream();
                        outputStream.write(bytes);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
            }
        });
        //发送消息
        sendMsgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              String content=textArea_1.getText();
              message.setText(content);
              message.setType("sendContent");
                byte[] bytes = Translate.ObjectToByte(message);
                try {
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(bytes);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                textArea_1.setText("");
            }
        });
        //发送文件
        sendFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isFile){
                    JOptionPane.showMessageDialog(null,"请先选择上传文件","错误", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    final String fileUrl=textArea_1.getText();
                    textArea_1.setText("");
                    new Thread(new Runnable() {
                       @Override
                       public void run() {
                           try {
                               Socket fileSocket = new Socket("localhost", 8081);
                               OutputStream socketOutputStream = fileSocket.getOutputStream();
                               InputStream socketInputStream = fileSocket.getInputStream();
                               byte[] bytes = new byte[1024];
                               FileInputStream fileInputStream = new FileInputStream(new File(fileUrl));
                               int len=0;

                               socketOutputStream.write(("sendFile"+fileUrl+".").getBytes());
                               int read = socketInputStream.read(bytes);
                               if(new String(bytes,0,read).equals("开始发送")){
                                   while ((len=fileInputStream.read(bytes))!=-1){
                                       socketOutputStream.write(bytes,0,len);
                                       System.out.println("+++");
                                   }
                                   textArea.append("文件发送完毕"+System.lineSeparator());
                                   fileSocket.close();
                                   fileInputStream.close();
                               }

                           } catch (IOException ioException) {
                               ioException.printStackTrace();
                           }
                       }
                   }).start();
                   isFile=false;
                }
            }
        });
        //选择文件
        fileSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                if(jFileChooser.showOpenDialog(frame)==JFileChooser.APPROVE_OPTION){
                    String name = jFileChooser.getSelectedFile().getAbsolutePath();
                    textArea_1.setText(name);
                }
                isFile=true;
            }
        });
        //表情包
        faceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final FaceGUI faceGUI = new FaceGUI(textArea_1);
                faceGUI.setVisible(true);
                faceGUI.setTitle("表情包");
                faceGUI.setBounds(300,400,386,260);
               faceGUI.setDefaultCloseOperation(HIDE_ON_CLOSE);
            }
        });
    }

    public void init(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 590, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("\u4F1A\u8BDD\u6D88\u606F\u7A97\u53E3");
        label.setBounds(14, 13, 90, 18);
        contentPane.add(label);

        JLabel label_1 = new JLabel("\u5728\u7EBF\u7528\u6237");
        label_1.setBounds(407, 13, 72, 18);
        contentPane.add(label_1);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(14, 30, 344, 118);
        contentPane.add(scrollPane);
        //消息框
        textArea = new JTextArea();
        scrollPane.setViewportView(textArea);

        JLabel label_2 = new JLabel("\u53D1\u8A00\u7A97\u53E3");
        label_2.setBounds(14, 161, 72, 18);
        contentPane.add(label_2);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(14, 183, 344, 93);
        contentPane.add(scrollPane_1);
        //发言框
        textArea_1 = new JTextArea();
        scrollPane_1.setViewportView(textArea_1);

        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(407, 30, 95, 296);
        contentPane.add(scrollPane_2);

        list = new JList();
        scrollPane_2.setViewportView(list);

         sendMsgButton = new JButton("\u53D1\u9001\u6D88\u606F");
        sendMsgButton.setBounds(245, 299, 113, 27);
        contentPane.add(sendMsgButton);

         sendFileButton = new JButton("发送文件");
        sendFileButton.setBounds(130, 299, 113, 27);
        contentPane.add(sendFileButton);
        //文件选择
         fileSelect = new JButton("");
        fileSelect.setIcon(new ImageIcon(ClientGUI.class.getResource("image/file.png")));
        fileSelect.getIcon();
        fileSelect.setBounds(79, 152, 26, 27);
        contentPane.add(fileSelect);

         faceButton = new JButton("");
        faceButton.setIcon(new ImageIcon(ClientGUI.class.getResource("image/face.png")));
        faceButton.setBounds(115, 152, 34, 27);
        contentPane.add(faceButton);
    }
}
