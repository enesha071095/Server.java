package SocketsTCP;

import ToTCPTransportConvert.PrepareDataToTCP;
import Tables.*;

import java.io.*;
import java.net.Socket;

public class AcceptedSocket extends Thread{
    private ListenerSocket ServerSocketListener;
    private Socket currentAcceptSocket;
    private boolean isGoExit = false;
    public boolean isHaveErrorInWork = false;


    public AcceptedSocket(Socket currentAcceptSocket, ListenerSocket ServerSocketListener) {
        this.currentAcceptSocket = currentAcceptSocket;
        this.ServerSocketListener = ServerSocketListener;
        start();
    }

    public void run(){
        try {
            ObjectOutputStream stream_out = new ObjectOutputStream(currentAcceptSocket.getOutputStream());
            ObjectInputStream stream_in = new ObjectInputStream(currentAcceptSocket.getInputStream());
            while (!isGoExit) {
                oneJob( stream_out,  stream_in);
            }
            exiting(stream_out, stream_in);
        } catch (Exception e){
            isHaveErrorInWork = true;
            isGoExit = true;
        }
    }

    private void oneJob(ObjectOutputStream stream_out, ObjectInputStream stream_in) throws Exception{
        String command = stream_in.readObject().toString();
        String data = stream_in.readObject().toString();
        String response = runCommand(command, data);
        isHaveErrorInWork = false;
        if(!response.equals("exit")) stream_out.writeObject(response);
    }

    private String runCommand(String command, String data){
        PrepareDataToTCP prepareDataToTCP = new PrepareDataToTCP();

        String response;
        switch (command){
            case "authorization":
                response = prepareDataToTCP.mainUserSearch(data);break;
            case "select_sessions":
                response = prepareDataToTCP.select_(data, OneSession.class);break;
            case "select_antes":
                response = prepareDataToTCP.select_(data, OneAnte.class);break;
            case "select_employers":
                response = prepareDataToTCP.select_(data, OneEmployer.class);break;
            case "select_users":
                response = prepareDataToTCP.select_(data, OneUser.class);break;
            case "delete":
                response = prepareDataToTCP.delete(data);break;
            case "insert_sessions":
                response = prepareDataToTCP.insert(data, "sessions", OneSession.class);break;
            case "insert_antes":
                response = prepareDataToTCP.insert(data, "antes", OneAnte.class);break;
            case "insert_employers":
                response = prepareDataToTCP.insert(data, "employers", OneEmployer.class);break;
            case "insert_users":
                response = prepareDataToTCP.insert(data, "users", OneUser.class);break;
            case "update_sessions":
                response = prepareDataToTCP.update(data, "sessions", OneSession.class);break;
            case "update_antes":
                response = prepareDataToTCP.update(data, "antes", OneAnte.class);break;
            case "update_employers":
                response = prepareDataToTCP.update(data, "employers", OneEmployer.class);break;
            case "update_users":
                response = prepareDataToTCP.update(data, "users", OneUser.class); break;
            default: case "exit":
                isGoExit = true; response = "exit"; break;
        }
        return response;
    }

    public void exiting(ObjectOutputStream stream_out, ObjectInputStream stream_in){
        try {
            closeAllResourses(stream_out, stream_in);
            isHaveErrorInWork = false;
        } catch (IOException e) {
            isHaveErrorInWork = true;
        } finally {
            ServerSocketListener.dialog.LabelList.get(2).setText("Клиентов подключено: " + (--ServerSocketListener.countClients));
        }
    }

    private void closeAllResourses(ObjectOutputStream stream_out, ObjectInputStream stream_in)
        throws IOException {
        stream_in.close();
        stream_out.close();
        currentAcceptSocket.close();
        ServerSocketListener.acceptClientsSocket.remove(this);
    }


}
