package model;

import java.util.Random;

/**
 * This class store the real chess information.
 * The Chessboard has 8 * 8 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;

    public Chessboard(int randomSeed) {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];

        initGrid();
        initPieces(randomSeed);
    }
    public Chessboard(int[][] a){
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];
        initGrid();
        setPieces(a);
    }
    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }
    public boolean CanEliminate(){
        for(int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (grid[i][j].getPiece() == null) {
                    return false;
                }
            }
        }
        for(int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++){
            for(int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (i + 2 < Constant.CHESSBOARD_ROW_SIZE.getNum()) {
                    if (grid[i][j].getPiece().getName().equals(grid[i + 1][j].getPiece().getName()) && grid[i][j].getPiece().getName().equals(grid[i + 2][j].getPiece().getName()))
                        return true;
                }
                if (j + 2 < Constant.CHESSBOARD_COL_SIZE.getNum()){
                    if(grid[i][j].getPiece().getName().equals(grid[i][j + 1].getPiece().getName()) && grid[i][j].getPiece().getName().equals(grid[i][j + 2].getPiece().getName()))
                        return true;
                }
            }
        }
        return false;
    }
    public void complement(int randomSeed){
        Random random = new Random(randomSeed);
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (grid[i][j].getPiece() == null) {
                    grid[i][j].setPiece(new ChessPiece(Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
                }
            }
        }
    }
    private void initPieces(int randomSeed) {
        Random random = new Random(randomSeed);
        do {
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    grid[i][j].setPiece(new ChessPiece(Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
                }
            }
        }
        while(CanEliminate());//初始棋盘不能出现连续的三个相同的chess
    }
    private void setPieces(int a[][]){
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].setPiece(new ChessPiece(Constant.findMap.get(a[i][j])));
            }
        }
    }
    private ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    public ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }


    public void swapChessPiece(ChessboardPoint point1, ChessboardPoint point2) {
        var p1 = getChessPieceAt(point1);
        var p2 = getChessPieceAt(point2);
        setChessPiece(point1, p2);
        setChessPiece(point2, p1);
    }


    public Cell[][] getGrid() {
        return grid;
    }



}
