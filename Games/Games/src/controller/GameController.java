package controller;

import listener.GameListener;
import model.Constant;
import model.Chessboard;
import model.ChessboardPoint;
import view.CellComponent;
import view.ChessComponent;
import view.ChessboardComponent;

import javax.swing.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and
 * onPlayerClickChessPiece()]
 *
 */
public class GameController implements GameListener {

    private Chessboard model;
    private ChessboardComponent view;

    // Record whether there is a selected piece before

    private ChessboardPoint selectedPoint;
    private ChessboardPoint selectedPoint2;

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;

        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
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
    public void onPlayerNewGame(){
        model = new Chessboard(0);
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                view.removeChessComponentAtGrid(new ChessboardPoint(i, j));
            }
        }
        view.initiateChessComponent(model);
        view.repaint();
        selectedPoint = null;
        selectedPoint2 = null;
    }
    public void onPlayerLoadGameFromFile(String path){
        File file = new File(path);
        Scanner in;
        try{
            in = new Scanner(file);
            for(int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++){
                String[] Line = in.nextLine().split(",");
                for(int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++){

                }
            }
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }
    }
    @Override
    public void onPlayerSwapChess(){
        // TODO: Init your swap function here.
        if(selectedPoint != null && selectedPoint2 != null){
            model.swapChessPiece(selectedPoint, selectedPoint2);
            var point1 = (ChessComponent)view.getGridComponentAt(selectedPoint).getComponent(0);
            var point2 = (ChessComponent)view.getGridComponentAt(selectedPoint2).getComponent(0);
            point1.setSelected(false);
            point2.setSelected(false);
            point1.repaint();
            point2.repaint();
            if(!model.CanEliminate()){
                //swap back
                model.swapChessPiece(selectedPoint, selectedPoint2);
                JOptionPane.showMessageDialog(null,"You can't swap these two chesses");
                selectedPoint = null;
                selectedPoint2 = null;
                return;
            }
            //ChessComponent tmp1, tmp2;
            view.removeChessComponentAtGrid(selectedPoint);
            view.removeChessComponentAtGrid(selectedPoint2);
            view.setChessComponentAtGrid(selectedPoint, point2);
            view.setChessComponentAtGrid(selectedPoint2, point1);
            view.repaint();
            selectedPoint = null;
            selectedPoint2 = null;
            //Eliminate();
            view.repaint();
        }
        //System.out.println("Implement your swap here.");
    }

    /*private void Eliminate(){

        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                int k;
                for(k = i + 1; k < Constant.CHESSBOARD_ROW_SIZE.getNum(); k++){
                    if(!model.getGrid()[i][j].getPiece().getName().equals(model.getGrid()[k][j].getPiece().getName())){
                        break;
                    }
                }
                if(k - i + 1 >= 3){
                    for(int l = i; l <= k; l++){
                        model.removeChessPiece(new ChessboardPoint(k, j));
                        view.removeChessComponentAtGrid(new ChessboardPoint(k, j));
                    }
                }
            }
        }
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                int k;
                for(k = j + 1; k < Constant.CHESSBOARD_COL_SIZE.getNum(); k++){
                    if(!model.getGrid()[i][j].getPiece().getName().equals(model.getGrid()[i][k].getPiece().getName())){
                        break;
                    }
                }
                if(k - j + 1 >= 3){
                    for(int l = j; l <= k; l++){
                        model.removeChessPiece(new ChessboardPoint(i, k));
                        view.removeChessComponentAtGrid(new ChessboardPoint(i, k));
                    }
                }
            }
        }
    }
    */
    @Override
    public void onPlayerNextStep(){
        // TODO: Init your next step function here.
        System.out.println("Implement your next step here.");
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if(selectedPoint2 != null){
            var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());
            var distance2point2 = Math.abs(selectedPoint2.getCol() - point.getCol()) + Math.abs(selectedPoint2.getRow() - point.getRow());
            var point1 = (ChessComponent)view.getGridComponentAt(selectedPoint).getComponent(0);
            var point2 = (ChessComponent)view.getGridComponentAt(selectedPoint2).getComponent(0);
            if(distance2point1 == 0 && point1!= null){
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = null;
            }else if(distance2point2 == 0 && point2!= null){
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = null;
            }else if(distance2point1 == 1 && point2!= null){
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            }else if(distance2point2 == 1 && point1!= null){
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
        
        if(distance2point1 == 0){
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            return;
        }

        if(distance2point1 == 1){
            selectedPoint2 = point;
            component.setSelected(true);
            component.repaint();
        }else{
            selectedPoint2 = null;
            
            var grid = (ChessComponent)view.getGridComponentAt(selectedPoint).getComponent(0);
            if(grid == null) return;            
            grid.setSelected(false);
            grid.repaint();
            
            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
        }
    }
}
