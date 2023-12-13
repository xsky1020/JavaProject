package model;

public enum Version {
    EASYVERSION("easy version", 0, 10, 200),
    NORMALVERSION("normal version", 1, 6, 200),
    HARDVERSION("hard version", 2, 3, 200);
    private String name;
    private int versionNum;
    private int stepLeft;
    private int score;
    private Version(String name, int versionNum, int stepLeft, int score){
        this.name = name;
        this.versionNum = versionNum;
        this.stepLeft = stepLeft;
        this.score = score;
    }

    public int getScore() {
        return score;
    }
    public int getStepLeft() {
        return stepLeft;
    }
    public int getVersionNum(){
        return versionNum;
    }
    public String getName(){
        return this.name;
    }
    public static Version getNextVersion(Version version){
        if(version == Version.EASYVERSION)
            return Version.NORMALVERSION;
        if(version == Version.NORMALVERSION)
            return Version.HARDVERSION;
        return Version.HARDVERSION;
    }
    public static Version getVersion(int x){
        if(x == 0)
            return Version.EASYVERSION;
        else if(x == 1)
            return Version.NORMALVERSION;
        else
            return Version.HARDVERSION;
    }
    @Override
    public String toString(){
        return this.name;
    }
}
