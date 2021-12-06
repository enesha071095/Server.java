package MainPackage;

import DialogSWing.NewDialog;
import DialogSWing.FirstWindowCreater;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private NewDialog firstMainDialog;
    private String error_message = "";

    public static void main(String[] args) {
        Main starter = new Main();
        starter.start();
    }

    public void start(){
        firstMainDialog = new NewDialog("Хеббекулиева Энеш", 300, 230);
        firstMainDialog.addLabel(250, 30, 25, 10, "Сервер НЕ Активен!");
        firstMainDialog.addLabel(250, 30, 25, 40, "Порт Устройства:");
        firstMainDialog.addTextField(250, 30, 25, 90);
        firstMainDialog.addButton(250, 30, 25, 140, "Слушать");

        firstMainDialog.FieldList.get(0).setText(String.valueOf(7777));
        firstMainDialog.ButtonList.get(0).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                error_message = "";
                if(isGetDataFromFile()) {
                    new FirstWindowCreater(Integer.valueOf(firstMainDialog.FieldList.get(0).getText()), firstMainDialog);
                    firstMainDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(firstMainDialog, "ERROR:\r\n" + error_message);
                }
            }
        });
        firstMainDialog.setVisible(true);
    }


    public static boolean isInt(String stringMessage){
        try {
            Integer.valueOf(stringMessage);
            return true;
        } catch (Exception e){
            return false;
        }
    }


    private boolean isGetDataFromFile() {
        if(!Main.isInt(firstMainDialog.FieldList.get(0).getText())){
            error_message += " Некорректныо - не Integer,";
        } else {
            if(Integer.valueOf(firstMainDialog.FieldList.get(0).getText()) <= 0
                    || Integer.valueOf(firstMainDialog.FieldList.get(0).getText()) > 9999){
                error_message += " Порт в Слишком велик,";
            }
        }
        return error_message.equals("") ? true : false;
    }
}
