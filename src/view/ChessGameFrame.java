package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Locale;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;

    //private JTextField StepLeft;
    //private int stepLeft;
    private ChessboardComponent chessboardComponent;
    private ScoreComponent Score;
    private StepLeftComponent StepLeft;
    public ChessGameFrame(int width, int height) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addChessboard();
        addScoreComponent();
        addStepLeftComponent();
        addSaveButton();
        addSwapConfirmButton();
        addNextStepButton();
        addLoadButton();
        addNewGameButton();
        this.StepLeft.setStepleft(10);
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }
    public ScoreComponent getScore(){
        return this.Score;
    }
    public StepLeftComponent getStepLeft(){
        return this.StepLeft;
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
    private void addScoreComponent(){
        Score = new ScoreComponent(this, WIDTH, HEIGTH);
    }
    private void addStepLeftComponent(){
        StepLeft = new StepLeftComponent(this, WIDTH, HEIGTH);
    }
    /**
     * 在游戏面板中添加标签 Your score
     */
    /*private void addLabel() {
        JLabel statusLabel = new JLabel("Your score");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }*/
    /*private void addLabel2() {
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
    }*/
    /*
    private void addScoreTextField(){
        Count = new JTextField("0");
        Count.setLocation(HEIGTH, HEIGTH / 10 + 60);
        Count.setFont(new Font("Rockwell", Font.BOLD, 20));
        Count.setSize(100, 30);
        Count.setEditable(false);
        add(Count);
        Count.setText("100");
    }*/
    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            System.out.println("Click save");
            JFileChooser jfc=new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
            jfc.showDialog(new JLabel(), "选择");
            File file=jfc.getSelectedFile();
            if(file.isDirectory()){
                System.out.println("文件夹:"+file.getAbsolutePath());
            }
            else if(file.isFile()){
                System.out.println("文件:"+file.getAbsolutePath());
            }
            System.out.println(jfc.getSelectedFile().getName());
            String path = String.valueOf(file);
            chessboardComponent.saveGameToFile(path);
        });
    }

    private void addSwapConfirmButton() {
        JButton button = new JButton("Confirm Swap");
        button.addActionListener((e) -> {
            chessboardComponent.swapChess();
            if(StepLeft.getStepleft() == 0){
                //JOptionPane.showMessageDialog(null, "You have usd out your steps!");
                Interaction.show("You have usd out your steps!");
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
        button.addActionListener((e) -> {
            chessboardComponent.nextStep();
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 280);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private boolean loadVerify(File file){
        String filename = file.getName();
        String suffix = filename.substring(filename.lastIndexOf('.'));
        if (!suffix.toLowerCase(Locale.ROOT).equals(".txt")){
            JOptionPane.showMessageDialog(this, "101");
            return false;
        }
        return true;
    }
    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            System.out.println("Click load");
            JFileChooser jfc=new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
            jfc.showDialog(new JLabel(), "选择");
            File file=jfc.getSelectedFile();
            if (loadVerify(file)){
            if(file.isDirectory()){
                System.out.println("文件夹:"+file.getAbsolutePath());
            }else if(file.isFile()){
                System.out.println("文件:"+file.getAbsolutePath());
            }
            System.out.println(jfc.getSelectedFile().getName());
            String path = String.valueOf(file);
            chessboardComponent.loadGameFromFile(path);}

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
            chessboardComponent.newGame();
        });
    }
}
