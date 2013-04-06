package stefanholzmueller.pp2.check;

import stefanholzmueller.pp2.util.IntTriple;

public interface OutcomeCalculator {

	CheckResult calculateResult(Check check, IntTriple dice);

}