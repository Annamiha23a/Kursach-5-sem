package mediator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Mediator {
    private ServerSocket serverSocket = null;
    public static int connectionsCounter = 0;


    public Mediator(){
        System.out.println("-------1-------");
        try{
            System.out.println("-------2-------");
            Socket clientSocket = null;
            try{
                System.out.println("-------3-------");
                serverSocket = new ServerSocket(2625);
                System.out.println("-------4-------");
                System.out.println("-------Сервер запущен-------");
                while(true){
                    clientSocket = serverSocket.accept();
                    Runnable run = new ThreadEchoHandler(clientSocket);
                    Thread newThread = new Thread(run);
                    newThread.start();
                    System.out.println("Новое подключение...\nКоличество активных подключений: " + ++connectionsCounter + "\n");
                }
            }
            finally{
                clientSocket.close();
                serverSocket.close();
            }
        }
        catch(IOException e)
        {

        }
    }

    public static void main(String args[]){
        new Mediator();
    }
}
