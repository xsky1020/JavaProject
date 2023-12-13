package view;

import javax.swing.*;
import java.awt.*;

public class ScoreComponent {
    private int WIDTH, HEIGTH;
    private JTextField Count;
    private JLabel ScoreLabel;
    private ChessGameFrame frame;
    private int score;
    public ScoreComponent(ChessGameFrame f, int width, int height) {
        this.WIDTH = width;
        this.HEIGTH = height;
        Count = new JTextField("0");
        Count.setFont(new Font("Rockwell", Font.BOLD, 20));
        Count.setSize(100,30);
        Count.setEditable(false);
        Count.setLocation(HEIGTH, HEIGTH / 10 + 60);
        ScoreLabel = new JLabel("Your score");
        ScoreLabel.setLocation(HEIGTH, HEIGTH / 10);
        ScoreLabel.setSize(200, 60);
        ScoreLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        this.score = 0;
        Count.setText(this.score + "");
        frame = f;
        frame.add(Count);
        frame.add(ScoreLabel);
    }
    public void addScore(int x){
        this.score = this.score + x;
        this.Count.setText(this.score + "");
    }
    public void clear(){
        this.score = 0;
        this.Count.setText("0");
    }
    public void setScore(int score){
        this.score = score;
        this.Count.setText(this.score+"");
    }
    public int getScore(){
     return score;
    }
}
