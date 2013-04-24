package stefanholzmueller.pp2.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import stefanholzmueller.pp2.check.OutcomeImpl.OutcomeEnum;

public class OutcomeImplTest {

	@Test(dataProvider = "isSuccessful")
	public void isSuccessful(OutcomeEnum outcomeEnum, boolean expected) {
		Outcome outcome = new OutcomeImpl(outcomeEnum, null, null);
		assertThat(outcome.isSuccessful(), is(expected));
	}

	@DataProvider(name = "isSuccessful")
	public Object[][] isSuccessful() {
		return new Object[][] { { OutcomeEnum.AUTOMATIC_FAILURE, false },
				{ OutcomeEnum.AUTOMATIC_SUCCESS, true },
				{ OutcomeEnum.SPECTACULAR_FAILURE, false },
				{ OutcomeEnum.SPECTACULAR_SUCCESS, true },
				{ OutcomeEnum.SPRUCHHEMMUNG, false },
				{ OutcomeEnum.SUCCESS, true }, { OutcomeEnum.FAILURE, false } };
	}

}
