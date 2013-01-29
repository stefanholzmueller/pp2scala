package stefanholzmueller.pp2.check;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import stefanholzmueller.pp2.util.IntTriple;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class CheckRoll {

    private Check check;
    private Integer die1;
    private Integer die2;
    private Integer die3;

    public CheckRoll() {
        // for JAXB
    }

    public CheckRoll(Check check, Integer die1, Integer die2, Integer die3) {
        this.check = check;
        this.die1 = die1;
        this.die2 = die2;
        this.die3 = die3;
    }

    public Check getCheck() {
        return check;
    }

    public IntTriple getDice() {
        return new IntTriple(die1, die2, die3);
    }

}
