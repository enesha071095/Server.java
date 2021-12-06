package SocketsTCP;

import DialogSWing.NewDialog;

import java.net.ServerSocket;
import java.util.ArrayList;


public class ListenerSocket extends Thread {
    public ArrayList<AcceptedSocket> acceptClientsSocket = new ArrayList<>();
    private ServerSocket serverSocket = null;
    public int countClients = 0;
    private int serverPort;
    public boolean isHaveErrorInWork = false;
    public NewDialog dialog;

    public ListenerSocket(int serverPort , NewDialog dialog) {
        this.serverPort = serverPort;
        this.dialog = dialog;
        start();
    }

    public void run(){
        try {
            serverSocket = new ServerSocket(serverPort);
            while(true) {
                acceptClientsSocket.add(new AcceptedSocket(serverSocket.accept(), this));
                dialog.LabelList.get(2).setText("Клиентов подключено: " + (++countClients));
            }
        } catch (Exception e) {
            isHaveErrorInWork = true;
        }
    }

    public void exitServer() {
        try {
            for (int i = 0; i < acceptClientsSocket.size(); i++) {
                acceptClientsSocket.get(i).interrupt();
            }
        } catch (Exception e) {
            isHaveErrorInWork = true;
        }
        interrupt();
    }


}
