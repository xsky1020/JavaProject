package controller;

import listener.GameListener;
import model.Constant;
import model.Chessboard;
import model.ChessboardPoint;
import model.Version;
import view.CellComponent;
import view.ChessComponent;
import view.ChessboardComponent;
import view.Interaction;
import view.ScoreComponent;
import view.StepLeftComponent;

import javax.swing.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and
 * onPlayerClickChessPiece()]
 */
public class GameController implements GameListener {

    private Chessboard model;
    private ChessboardComponent view;
    //private ChessScoreComponent score;
    // Record whether there is a selected piece before

    private ChessboardPoint selectedPoint;
    private ChessboardPoint selectedPoint2;
    private ScoreComponent score;
    private StepLeftComponent stepLeft;
    private Version version;
    public GameController(ChessboardComponent view, Chessboard model, ScoreComponent score, StepLeftComponent stepLeft) {
        this.view = view;
        this.model = model;
        this.score = score;
        this.stepLeft = stepLeft;
        //this.score = new ChessScoreComponent();
        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
        //score.repaint();
        initVersion();
        Claim();
        score.clear();
        stepLeft.setStepleft(version.getStepLeft());
    }

    private void initialize() {

        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
    }

    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
    }
    public void initVersion(){
        this.version = Interaction.chooseVersion();
    }
    public void onPlayerNewGame() {
        initVersion();
        //Claim();
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (model.getGrid()[i][j].getPiece() != null)
                    view.removeChessComponentAtGrid(new ChessboardPoint(i, j));
            }
        }
        stepLeft.setStepleft(version.getStepLeft());
        score.clear();
        model = new Chessboard(0);
        view.initiateChessComponent(model);
        view.repaint();
        selectedPoint = null;
        selectedPoint2 = null;
        Claim();
    }
    public void onPlayerNewGame(Version version) {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (model.getGrid()[i][j].getPiece() != null)
                    view.removeChessComponentAtGrid(new ChessboardPoint(i, j));
            }
        }
        model = new Chessboard(0);
        view.initiateChessComponent(model);
        view.repaint();
        selectedPoint = null;
        selectedPoint2 = null;
        this.version = version;
        stepLeft.setStepleft(version.getStepLeft());
        score.clear();
        Claim();
    }
    public void onPlayerLoadGameFromFile(String path) {
        File file = new File(path);
        Scanner in;
        try {
            in = new Scanner(file);
            int[][] a = new int[Constant.CHESSBOARD_ROW_SIZE.getNum()][];
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                a[i] = new int[Constant.CHESSBOARD_COL_SIZE.getNum()];
            }
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                String[] Line = in.nextLine().split(",");
                if (Line.length != 8) {
                    JOptionPane.showMessageDialog(view, "102");
                    return;
                }
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    a[i][j] = Integer.parseInt(Line[j]);

                }
            }
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    if(model.getGrid()[i][j].getPiece() != null)
                        view.removeChessComponentAtGrid(new ChessboardPoint(i, j));
                }
            }
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    if (a[i][j] != 1 && a[i][j] != 2 && a[i][j] != 3 && a[i][j] != 4) {
                        JOptionPane.showMessageDialog(view, "103");
                        return;
                    }
                }
            }
            String lineScore = in.nextLine();
            if (lineScore.length() != 3 && lineScore.length() != 2 && lineScore.length() != 1 && lineScore.length() != 4) {
                JOptionPane.showMessageDialog(view, "102");
                return;
            }
            score.setScore(Integer.parseInt(lineScore));
            String lineStep = in.nextLine();
            stepLeft.setStepleft(Integer.parseInt(lineStep));
            String lineVersion = in.nextLine();
            if(lineVersion.length() != 1) {
                JOptionPane.showMessageDialog(view, "102");
                return;
            }
            int versionNum = Integer.parseInt(lineVersion);
            if(versionNum != 0 && versionNum != 1 && versionNum != 2){
                JOptionPane.showMessageDialog(view, "102");
                return;
            }
            version = Version.getVersion(versionNum);
            model = new Chessboard(a);
            view.initiateChessComponent(model);
            view.repaint();
            Claim2();
            selectedPoint = null;
            selectedPoint2 = null;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public void onPlayerSaveGameToFile(String path) {
        List<String> lines = new ArrayList<>();
        File file = new File(path);
        for (int i = 0; i < 8; i++) {
            String Row = "";
            for (int j = 0; j < 8; j++) {
                Row += Constant.findNum.get(model.getGrid()[i][j].getPiece().getName()) + ",";
            }
            lines.add(Row);
        }
        String Score = score.getScore() + "";
        String steps = stepLeft.getStepleft() + "";
        String v = version.getVersionNum() + "";
        lines.add(Score);
        lines.add(steps);
        lines.add(v);
        try {
            Files.write(Path.of(path), lines, Charset.defaultCharset());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void checkState(){
        if(score.getScore() >= version.getScore()){
            WinRespond();
        }
        if(stepLeft.getStepleft() == 0){
            FailRespond();
        }
    }
    private void Claim(){
        Interaction.show("This game is " + version.toString() + ", You have "+ version.getStepLeft() + " steps and you need to get " + version.getScore() + " scores.");
    }
    private void Claim2(){
        Interaction.show("This game is " + version.toString() + ", You have left "+ version.getStepLeft() + " steps and you have got " + score.getScore() + " scores. You need to get " + version.getScore() + " scores.");
    }
    private void WinRespond(){
        Interaction.show("You win!");
        if(version == Version.HARDVERSION){
            Interaction.show("Let's start a new game!");
            onPlayerNewGame();
        }
        else {
            int st = Interaction.WinnerChoose();
            if (st == 0) {
                onPlayerNewGame();
            } else {
                onPlayerNewGame(Version.getNextVersion(version));
            }
        }
    }
    private void FailRespond(){
        Interaction.show("Your have run out of your step");
        int st = Interaction.LoserChoose();
        if(st == 0){
            onPlayerNewGame();
        }
        else {
            onPlayerNewGame(version);
        }
    }
    @Override
    public void onPlayerSwapChess() {
        if (model.CanEliminate()) {
            Interaction.show("Click Next Step button first");
            return;
        }
        // TODO: Init your swap function here.
        if (selectedPoint != null && selectedPoint2 != null) {
            model.swapChessPiece(selectedPoint, selectedPoint2);
            var point1 = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            var point2 = (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0);
            point1.setSelected(false);
            point2.setSelected(false);
            point1.repaint();
            point2.repaint();
            if (!model.CanEliminate()) {
                //swap back
                model.swapChessPiece(selectedPoint, selectedPoint2);
                JOptionPane.showMessageDialog(null, "You can't swap these two chesses");
                selectedPoint = null;
                selectedPoint2 = null;
                return;
            }
            view.setChessComponentAtGrid(selectedPoint, point2);
            view.setChessComponentAtGrid(selectedPoint2, point1);
            view.repaint();
            selectedPoint = null;
            selectedPoint2 = null;
            Eliminate();
            stepLeft.swapUpdate();
            view.repaint();
            checkState();
        }
        else {
            Interaction.show("Please selected two points first");
        }
        //System.out.println("Implement your swap here.");
    }

    private int[][] NeedEliminate() {
        int[][] res = new int[Constant.CHESSBOARD_ROW_SIZE.getNum()][];
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            res[i] = new int[Constant.CHESSBOARD_COL_SIZE.getNum()];
        }
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                res[i][j] = 0;
            }
        }
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                int k;
                for (k = i + 1; k < Constant.CHESSBOARD_ROW_SIZE.getNum(); k++) {
                    if (!model.getGrid()[i][j].getPiece().getName().equals(model.getGrid()[k][j].getPiece().getName())) {
                        break;
                    }
                }
                if (k - i >= 3) {
                    for (int l = i; l < k; l++) {
                        //model.removeChessPiece(new ChessboardPoint(k, j));
                        //view.removeChessComponentAtGrid(new ChessboardPoint(k, j));
                        res[l][j] = Constant.findNum.get(model.getGrid()[i][j].getPiece().getName());
                    }
                }
            }
        }
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                int k;
                for (k = j + 1; k < Constant.CHESSBOARD_COL_SIZE.getNum(); k++) {
                    if (!model.getGrid()[i][j].getPiece().getName().equals(model.getGrid()[i][k].getPiece().getName())) {
                        break;
                    }
                }
                if (k - j >= 3) {
                    for (int l = j; l < k; l++) {
                        //model.removeChessPiece(new ChessboardPoint(i, k));
                        //view.removeChessComponentAtGrid(new ChessboardPoint(i, k));
                        res[i][l] = Constant.findNum.get(model.getGrid()[i][j].getPiece().getName());
                    }
                }
            }
        }
        return res;
    }

    private void Eliminate() {
        if (!model.CanEliminate()) {
            return;
        }
        int[][] e = NeedEliminate();
        boolean[][] tmp1 = new boolean[Constant.CHESSBOARD_ROW_SIZE.getNum()][];
        boolean[][] tmp2 = new boolean[Constant.CHESSBOARD_COL_SIZE.getNum()][];
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            tmp1[i] = new boolean[Constant.CHESSBOARD_COL_SIZE.getNum()];
            tmp2[i] = new boolean[Constant.CHESSBOARD_COL_SIZE.getNum()];
        }
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                view.removeChessComponentAtGrid(new ChessboardPoint(i, j));
            }
        }
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (e[i][j] > 0) {
                    model.removeChessPiece(new ChessboardPoint(i, j));
                    //view.removeChessComponentAtGrid(new ChessboardPoint(i, j));
                    //System.out.println(view.getGridComponentAt(new ChessboardPoint(i, j)));
                    //view.repaint();
                }
                tmp1[i][j] = false;
                tmp2[i][j] = false;
                //System.out.print(e[i][j] + " ");
            }
            //System.out.println();
        }
        view.initiateChessComponent(model);
        view.repaint();
        int dscore = 0;
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (tmp1[i][j])
                    continue;
                if (e[i][j] == 0)
                    continue;
                int k;
                for (k = i + 1; k < Constant.CHESSBOARD_ROW_SIZE.getNum(); k++) {
                    if (e[k][j] != e[i][j])
                        break;
                }
                if (k - i >= 3) {
                    for (int l = i; l < k; l++) {
                        tmp1[l][j] = true;
                    }
                    dscore += (k - i) * 10;
                }
            }
        }
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (tmp2[i][j])
                    continue;
                if (e[i][j] == 0)
                    continue;
                int k;
                for (k = j + 1; k < Constant.CHESSBOARD_COL_SIZE.getNum(); k++) {
                    if (e[i][k] != e[i][j])
                        break;
                }
                if (k - j >= 3) {
                    for (int l = j; l < k; l++) {
                        tmp2[i][l] = true;
                    }
                    dscore += (k - j) * 10;
                }
            }
        }
        score.addScore(dscore);
    }

    private void ChessDown() {
        for (int i = Constant.CHESSBOARD_ROW_SIZE.getNum() - 1; i >= 0; i--) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (model.getGrid()[i][j].getPiece() == null)
                    continue;
                int k = i;
                while ((k + 1 < Constant.CHESSBOARD_ROW_SIZE.getNum() - 1) && model.getGrid()[k + 1][j].getPiece() == null) {
                    ChessboardPoint p1 = new ChessboardPoint(k, j);
                    ChessboardPoint p2 = new ChessboardPoint(k + 1, j);
                    model.swapChessPiece(p1, p2);
                    var point1 = (ChessComponent) view.getGridComponentAt(p1).getComponent(0);
                    view.setChessComponentAtGrid(p2, point1);
                    //view.removeChessComponentAtGrid(p1);
                    k++;
                }
            }
        }
        view.repaint();
    }

    private void Complement() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (model.getGrid()[i][j].getPiece() != null)
                    view.removeChessComponentAtGrid(new ChessboardPoint(i, j));
            }
        }
        boolean[][] tmp = new boolean[Constant.CHESSBOARD_ROW_SIZE.getNum()][];
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            tmp[i] = new boolean[Constant.CHESSBOARD_COL_SIZE.getNum()];
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                tmp[i][j] = false;
                if (model.getGrid()[i][j].getPiece() == null) {
                    tmp[i][j] = true;
                }
            }
        }
        model.complement(0);
        view.initiateChessComponent(model);
        view.repaint();
    }

    @Override
    public void onPlayerNextStep() {
        // TODO: Init your next step function here.
        // System.out.println("Implement your next step here.");
        Eliminate();
        checkState();
        ChessDown();
        Complement();
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint2 != null) {
            var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());
            var distance2point2 = Math.abs(selectedPoint2.getCol() - point.getCol()) + Math.abs(selectedPoint2.getRow() - point.getRow());
            var point1 = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            var point2 = (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0);
            if (distance2point1 == 0 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = null;
            } else if (distance2point2 == 0 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = null;
            } else if (distance2point1 == 1 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            } else if (distance2point2 == 1 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            }
            return;
        }


        if (selectedPoint == null) {
            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
            return;
        }

        var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());

        if (distance2point1 == 0) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            return;
        }

        if (distance2point1 == 1) {
            selectedPoint2 = point;
            component.setSelected(true);
            component.repaint();
        } else {
            selectedPoint2 = null;

            var grid = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            if (grid == null) return;
            grid.setSelected(false);
            grid.repaint();

            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
        }
    }
}
