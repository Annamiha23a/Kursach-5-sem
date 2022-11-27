package view.MainFrame;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainFrame {
    public static Socket clientSocket;
    public static ObjectOutputStream output;
    public static ObjectInputStream input;
    public static int user_id;

    public static void main(String args[]){
        try{
            clientSocket = new Socket("127.0.0.1", 2626);
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
