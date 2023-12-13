package view;

import javax.swing.*;
import java.awt.*;
public class StepLeftComponent {
    private int WIDTH, HEIGTH;
    private JTextField Count;
    private JLabel StepLeftLabel;
    private int stepleft;
    private ChessGameFrame frame;
    public StepLeftComponent(ChessGameFrame f, int width, int height){
        this.WIDTH = width;
        this.HEIGTH = height;
        Count = new JTextField("0");
        Count.setFont(new Font("Rockwell", Font.BOLD, 20));
        Count.setSize(100,30);
        Count.setEditable(false);
        Count.setLocation(HEIGTH, HEIGTH / 10 - 30);
        StepLeftLabel = new JLabel("Your step left");
        StepLeftLabel.setLocation(HEIGTH, HEIGTH / 10 - 90);
        StepLeftLabel.setSize(200, 60);
        StepLeftLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        stepleft = 10;
        Count.setText(stepleft + "");
        frame = f;
        frame.add(Count);
        frame.add(StepLeftLabel);
    }
    public void swapUpdate(){
        this.stepleft--;
        Count.setText(stepleft + "");
    }
    public int getStepleft(){
        return this.stepleft;
    }
    public void setStepleft(int x){
        this.stepleft = x;
        Count.setText(stepleft + "");
    }
}
