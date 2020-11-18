import model.Message;
import model.Translate;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientThread implements Runnable{
    Socket socket;
    JTextArea textArea;
    JList jList;

    public ClientThread(Socket socket, JTextArea textArea,JList jList) {
        this.socket = socket;
        this.textArea=textArea;
        this.jList=jList;
    }

    public void run() {
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            while(true){
                int len = inputStream.read(bytes);
                if(len<=0) continue;
                Message message = (Message) Translate.ByteToObject(bytes, 0, len);
                if(message.getText().equals("success")){
                    textArea.append(message.getUsername()+": 已上线"+System.lineSeparator());
                }
                else{
                    textArea.append(message.getText());
                }
                textArea.setCaretPosition(textArea.getDocument().getLength());
                jList.setListData(message.getUsers());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
