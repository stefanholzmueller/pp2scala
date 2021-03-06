package stefanholzmueller.pp2.check;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import stefanholzmueller.pp2.util.IntTriple;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Check {

	private int attribute1;
	private int attribute2;
	private int attribute3;
	private int value;
	private int difficulty;
	private boolean minimumQuality;
	private boolean festeMatrix;
	private boolean wildeMagie;
	private boolean spruchhemmung;

	public Check() {
		// for JAXB
	}

	public Check(int attribute1, int attribute2, int attribute3, int value) {
		this.attribute1 = attribute1;
		this.attribute2 = attribute2;
		this.attribute3 = attribute3;
		this.value = value;
	}

	public Check(int attribute1, int attribute2, int attribute3, int value,
			int difficulty) {
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

	public boolean hasMinimumQuality() {
		return minimumQuality;
	}

	public void setMinimumQuality(boolean minimumQualityUsed) {
		this.minimumQuality = minimumQualityUsed;
	}

	public boolean hasFesteMatrix() {
		return festeMatrix;
	}

	public void setFesteMatrix(boolean festeMatrix) {
		this.festeMatrix = festeMatrix;
	}

	public boolean hasWildeMagie() {
		return wildeMagie;
	}

	public void setWildeMagie(boolean wildeMagie) {
		this.wildeMagie = wildeMagie;
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
