package stefanholzmueller.pp2.check;

import stefanholzmueller.pp2.util.IntTriple;

public interface OutcomeCalculator {

	CheckResult examine(Check check, IntTriple dice);

}