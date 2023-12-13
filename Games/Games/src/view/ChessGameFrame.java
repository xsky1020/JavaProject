package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;

    private JTextField Count;
    private JTextField StepLeft;
    private int stepLeft;
    private ChessboardComponent chessboardComponent;
    public ChessGameFrame(int width, int height) {
        String[] s = {"Easy version", "Normal version", "Hard version"};
        int res = JOptionPane.showOptionDialog(null,"choose your version","",
                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,s,"中号");
        stepLeft = 10;
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        addLabel();
        addLabel2();
        addStepLeftTextField();
        addScoreTextField();
        addHelloButton();
        addSwapConfirmButton();
        addNextStepButton();
        addLoadButton();
        addNewGameButton();
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签 Your score
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("Your score");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }
    private void addLabel2() {
        JLabel statusLabel2 = new JLabel("Step left");
        statusLabel2.setLocation(HEIGTH, HEIGTH / 10 - 90);
        statusLabel2.setSize(200, 60);
        statusLabel2.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel2);
    }
    private void addStepLeftTextField(){
        StepLeft = new JTextField("0");
        StepLeft.setLocation(HEIGTH, HEIGTH / 10 - 30);
        StepLeft.setFont(new Font("Rockwell", Font.BOLD, 20));
        StepLeft.setSize(100, 30);
        StepLeft.setEditable(false);
        add(StepLeft);
        StepLeft.setText(stepLeft+ "");
    }
    private void addScoreTextField(){
        Count = new JTextField("0");
        Count.setLocation(HEIGTH, HEIGTH / 10 + 60);
        Count.setFont(new Font("Rockwell", Font.BOLD, 20));
        Count.setSize(100, 30);
        Count.setEditable(false);
        add(Count);
        Count.setText("100");
    }
    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addSwapConfirmButton() {
        JButton button = new JButton("Confirm Swap");
        button.addActionListener((e) -> {
            chessboardComponent.swapChess();
            stepLeft--;
            StepLeft.setText(stepLeft + "");
            if(stepLeft == 0){
                JOptionPane.showMessageDialog(null, "You have usd out your steps!");
                chessboardComponent.newGame();
            }
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addNextStepButton() {
        JButton button = new JButton("Next Step");
        button.addActionListener((e) -> chessboardComponent.nextStep());
        button.setLocation(HEIGTH, HEIGTH / 10 + 280);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this,"Input Path here");
            chessboardComponent.loadGameFromFile(path);
        });
    }
    private void addNewGameButton(){
        JButton button = new JButton("New Game");
        button.setLocation(HEIGTH, HEIGTH / 10 + 440);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            //this = ChessGameFrame(WIDTH, HEIGTH);
            //remove(chessboardComponent);
            //addChessboard();
            stepLeft = 10;
            StepLeft.setText(stepLeft + "");
            chessboardComponent.newGame();
        });
    }
}
