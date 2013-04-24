package stefanholzmueller.pp2.check;

import stefanholzmueller.pp2.util.IntTriple;

public interface OutcomeExaminer {

	OutcomeImpl examine(Check check, IntTriple dice);

}