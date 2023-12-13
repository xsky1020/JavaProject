package view;

import javax.swing.*;
import model.Version;
public class Interaction {
    public static void show(String s){
        JOptionPane.showMessageDialog(null, s);
    }
    public static Version chooseVersion(){
        String[] s = {"Easy version", "Normal version", "Hard version"};
        int res = JOptionPane.showOptionDialog(null,"choose your version","",
                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,s,"中号");
        switch (res) {
            case 0:
                return Version.EASYVERSION;
            case 1:
                return Version.NORMALVERSION;
            case 2:
                return Version.HARDVERSION;
        }
        return Version.EASYVERSION;
    }
    public static int WinnerChoose(){
        String[] s = {"start a new game", "try next version"};
        int res = JOptionPane.showOptionDialog(null,"choose your version","",
                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,s,"中号");
        return res;
    }
    public static int LoserChoose(){
        String[] s = {"start a new game", "try this version again"};
        int res = JOptionPane.showOptionDialog(null,"choose your version","",
                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,s,"中号");
        return res;
    }
}
