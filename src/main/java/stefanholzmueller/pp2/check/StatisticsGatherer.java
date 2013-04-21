package stefanholzmueller.pp2.check;

public interface StatisticsGatherer {

	CheckStatistics gather(Check check);

	public interface CheckStatistics {

		double getChance();

		double getAverageQuality();

	}

}