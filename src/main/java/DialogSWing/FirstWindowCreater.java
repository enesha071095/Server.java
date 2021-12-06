package DialogSWing;

import SocketsTCP.ListenerSocket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class FirstWindowCreater {
    private NewDialog frameDialogServer;
    NewDialog newDialogCurrent;
    private int portForStarted;
    private ListenerSocket ServerSocketListener;


    public FirstWindowCreater(int portForStarted, NewDialog frameDialogServer){
        this.frameDialogServer = frameDialogServer;
        this.portForStarted = portForStarted;
        addDefInfoInDialog();
        setEventForWindow();
        newDialogCurrent.setVisible(true);
    }

    private void addDefInfoInDialog(){
        newDialogCurrent = new NewDialog("Хеббекулиева Энеш", 350, 250);
        newDialogCurrent.addLabel(250, 25,25, 10, "Сервер работает!");
        newDialogCurrent.addLabel(250, 25,25, 60, "Запущен на порте: " + String.valueOf(portForStarted));
        newDialogCurrent.addLabel(250, 25,25, 110, "Клиентов подключено: 0");
        newDialogCurrent.addButton(250, 25, 25, 160, "EXIT");
    }

    private void setEventForWindow(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServerSocketListener.exitServer();
                if(ServerSocketListener.isHaveErrorInWork) JOptionPane.showMessageDialog(newDialogCurrent,"Некорректно вышли");
                frameDialogServer.close();
                newDialogCurrent.close();
            }
        };
        newDialogCurrent.ButtonList.get(0).addActionListener(actionListener);
        addWindowListener();
    }

    private void addWindowListener(){
        WindowListener windowListener = new WindowListener() {
            public void windowActivated(WindowEvent event) {}
            public void windowClosed(WindowEvent event) {}
            public void windowDeactivated(WindowEvent event) {}
            public void windowDeiconified(WindowEvent event) {}
            public void windowIconified(WindowEvent event) {}
            public void windowOpened(WindowEvent event) {
                ServerSocketListener = new ListenerSocket(portForStarted, newDialogCurrent);
                if(ServerSocketListener.isHaveErrorInWork){
                    JOptionPane.showMessageDialog(newDialogCurrent, "Ошибка запуска сервера");
                    newDialogCurrent.dispose();
                }
            }
            public void windowClosing(WindowEvent event) {
                ServerSocketListener.exitServer();
                if(ServerSocketListener.isHaveErrorInWork)JOptionPane.showMessageDialog(newDialogCurrent, "Ошибка закрытия сервера");

            }
        };
        newDialogCurrent.addWindowListener(windowListener);
    }


}
