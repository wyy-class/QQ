import db.DBUtil;
import model.Message;
import model.Translate;
import model.User;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class ServerThread implements Runnable{
    Socket socket;
    JTextArea content;
    ArrayList<Socket> lists;
    Vector<String> users;
    Message message;
    public ServerThread(Socket socket, JTextArea content, ArrayList<Socket> lists, Vector<String> users) {
        this.socket = socket;
        this.content = content;
        this.lists=lists;
        this.users=users;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            byte[] bytes = new byte[1024];
            while (true){
                int len = inputStream.read(bytes);
                if(len<=0) continue;
                 message = (Message) Translate.ByteToObject(bytes, 0, len);
                if(message.getType().equals("login")){
                    login2(outputStream);
                }
                else if(message.getType().equals("sendContent")){
                    sendContent();
                }
                else if(message.getType().equals("quit")){
                    quit();
                }
                else if(message.getType().equals("register")){
                    register();
                }
                else if(message.getType().equals("findPassword")){
                    findPassword();
                    System.out.println("++++");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void findPassword() throws IOException{
        String username = message.getUsername();
        OutputStream outputStream = socket.getOutputStream();
        try {
            System.out.println(message.toString());
            User user = DBUtil.getByUsername(username);
            if(user.getEmail().equals(message.getEmail())){
                user.setPassword(message.getPassword());
                DBUtil.update(user);
                outputStream.write("success".getBytes());
                content.append(message.getUsername()+": 修改密码成功"+System.lineSeparator());
            }
            else{
                outputStream.write("error".getBytes());
                content.append(message.getUsername()+": 修改密码失败"+System.lineSeparator());
            }
        }catch (NullPointerException e){
            outputStream.write("null_error".getBytes());
        }

    }

    private void register() throws IOException {
        String username=message.getUsername();
        OutputStream outputStream = socket.getOutputStream();
        if(DBUtil.register(username)){
            User user = new User();
            user.setUsername(message.getUsername());
            user.setPassword(message.getPassword());
            user.setEmail(message.getEmail());
            DBUtil.addUser(user);
            outputStream.write("success".getBytes());
            content.append(username+": 注册成功"+System.lineSeparator());
        }
        else{
            outputStream.write("error".getBytes());
            content.append(username+": 注册失败"+System.lineSeparator());
        }
    }

    private void quit() throws IOException {
        lists.remove(socket);
        users.remove(message.getUsername());
        message.setUsers(users);
        message.setText(message.getUsername()+"; "+"已退出"+System.lineSeparator());
        for(Socket s:lists){
            OutputStream outputStream1 = s.getOutputStream();
            outputStream1.write(Translate.ObjectToByte(message));
        }
        content.append(message.getUsername()+"; "+"已退出"+System.lineSeparator());
    }

    private void sendContent() {
        content.append(message.getUsername()+": "+message.getText()+System.lineSeparator());
        content.setCaretPosition(content.getDocument().getLength());
        message.setUsers(users);
        message.setText(message.getUsername()+": "+message.getText()+System.lineSeparator());
        try {
            for(Socket  s:lists){
                OutputStream outputStream = s.getOutputStream();
                outputStream.write(Translate.ObjectToByte(message));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login2(OutputStream outputStream) throws IOException {

        if(users.contains(message.getUsername())){
            message.setText("exist");
            content.append(message.getUsername()+": 账户已存在"+System.lineSeparator());
        }
        else{
            if(DBUtil.login(new User(message.getUsername(),message.getPassword()))){
                users.add(message.getUsername());
                message.setText("success");
                message.setUsers(users);
                lists.add(socket);
                for(Socket s:lists){
                    OutputStream outputStream1 = s.getOutputStream();
                    outputStream1.write(Translate.ObjectToByte(message));
                }
                content.append(message.getUsername()+": 已上线"+System.lineSeparator());
                return;
            }
            else{
                message.setText("error");
                content.append(message.getUsername()+": 密码或账户错误"+System.lineSeparator());
            }
        }
        content.setCaretPosition(content.getDocument().getLength());
        try {
                outputStream.write(Translate.ObjectToByte(message));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
