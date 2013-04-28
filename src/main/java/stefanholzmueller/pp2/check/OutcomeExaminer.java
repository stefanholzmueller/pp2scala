package stefanholzmueller.pp2.check;

import stefanholzmueller.pp2.util.IntTriple;

public interface OutcomeExaminer {

	Outcome examine(Check check, IntTriple dice);

}