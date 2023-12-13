package model;

public class Util {
    public static <T> T RandomPick(T[] arr){
        int randomIndex = (int) (Math.random() * arr.length);
        return arr[randomIndex];
    }
}
//从数组中随机选一个值
