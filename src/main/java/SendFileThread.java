
import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SendFileThread implements Runnable{
    JTextArea textArea;

    public SendFileThread(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(8081);
           while (true){
               final Socket socket = serverSocket.accept();
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           InputStream socketInputStream = socket.getInputStream();
                           OutputStream socketOutputStream = socket.getOutputStream();
                           byte[] bytes = new byte[1024];
                           FileOutputStream fileOutputStream=null;
                           String url="src\\main\\resources\\updatas\\";
                           while (true){
                               int len = socketInputStream.read(bytes);

                               if(len<=0){
                                   socketOutputStream.write("发送完毕".getBytes());
                                   textArea.append("发送完毕"+System.lineSeparator());
                                   break;
                               }
                               String request = new String(bytes, 0, len);
                               System.out.println("--"+request);
                               if(request.contains("sendFile")){
                                   String type=request.split("\\.")[1];
                                   String name = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
                                   url+=name+"."+type;
                                   fileOutputStream=new FileOutputStream(new File(url));
                                   socketOutputStream.write("开始发送".getBytes());
                               }
                               else{
                                   System.out.println("====");
                                   fileOutputStream.write(bytes,0,len);
                               }
                           } socket.close();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }
               }).start();
           }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
