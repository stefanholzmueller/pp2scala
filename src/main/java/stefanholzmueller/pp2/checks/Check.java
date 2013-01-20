package stefanholzmueller.pp2.checks;

public class Check {

    private int attribute1;
    private int attribute2;
    private int attribute3;
    private int value;
    private int difficulty;
    private boolean minimumEffect;
    private boolean festeMatrix;
    private boolean tollpatsch;
    private boolean spruchhemmung;

    public Check(int attribute1, int attribute2, int attribute3, int value) {
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.attribute3 = attribute3;
        this.value = value;
    }

    public Check(int attribute1, int attribute2, int attribute3, int value, int difficulty) {
        this(attribute1, attribute2, attribute3, value);
        this.difficulty = difficulty;
    }

    public int getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(int attribute1) {
        this.attribute1 = attribute1;
    }

    public int getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(int attribute2) {
        this.attribute2 = attribute2;
    }

    public int getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(int attribute3) {
        this.attribute3 = attribute3;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean hasMinimumEffect() {
        return minimumEffect;
    }

    public void setMinimumEffect(boolean minimumEffectUsed) {
        this.minimumEffect = minimumEffectUsed;
    }

    public boolean hasFesteMatrix() {
        return festeMatrix;
    }

    public void setFesteMatrix(boolean festeMatrix) {
        this.festeMatrix = festeMatrix;
    }

    public boolean hasTollpatsch() {
        return tollpatsch;
    }

    public void setTollpatsch(boolean tollpatsch) {
        this.tollpatsch = tollpatsch;
    }

    public boolean hasSpruchhemmung() {
        return spruchhemmung;
    }

    public void setSpruchhemmung(boolean spruchhemmung) {
        this.spruchhemmung = spruchhemmung;
    }

    public IntTriple getAttributeTriple() {
        return new IntTriple(attribute1, attribute2, attribute3);
    }

}
