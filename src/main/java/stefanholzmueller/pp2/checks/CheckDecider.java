package stefanholzmueller.pp2.checks;

public interface CheckDecider {

    CheckResult determineResult(Check check, IntTriple dice);

}
