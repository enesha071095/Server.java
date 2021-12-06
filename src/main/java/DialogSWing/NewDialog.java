package DialogSWing;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class NewDialog extends JFrame {
    private JPanel ThisDialogPanel;
    public ArrayList<JButton> ButtonList = new ArrayList<>();
    public ArrayList<JTextField> FieldList = new ArrayList<>();
    public ArrayList<JLabel> LabelList = new ArrayList<>();

    public NewDialog(String title_data, int width, int height){
        super(title_data);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        ThisDialogPanel = new JPanel();
        ThisDialogPanel.setLayout(null);
        setLocationRelativeTo(null);
    }


    public void addButton(int width, int height, int xPoint, int yPoint, String title_data){
        JButton myButton = new JButton(title_data);
        myButton.setSize(width, height);
        myButton.setLocation(xPoint, yPoint);

        ThisDialogPanel.add(myButton);
        setContentPane(ThisDialogPanel);

        ButtonList.add(myButton);
    }


    public void addTextField(int width, int height, int xPoint, int yPoint){
        JTextField myTextField = new JTextField("");
        myTextField.setSize(width, height);
        myTextField.setLocation(xPoint,yPoint);

        ThisDialogPanel.add(myTextField);

        FieldList.add(myTextField);
    }

    public void addLabel(int width, int height, int xPoint, int yPoint, String title_data){
        JLabel myLabel = new JLabel(title_data);
        myLabel.setSize(width, height);
        myLabel.setLocation(xPoint,yPoint);

        ThisDialogPanel.add(myLabel);

        LabelList.add(myLabel);
    }
    public void close(){
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

}




