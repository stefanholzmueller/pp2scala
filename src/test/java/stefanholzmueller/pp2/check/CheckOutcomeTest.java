package stefanholzmueller.pp2.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckOutcomeTest {

	@Test(dataProvider = "isSuccessful")
	public void isSuccessful(CheckOutcome checkSuccess, boolean expected)
			throws Exception {
		assertThat(checkSuccess.isSuccessful(), is(expected));
	}

	@DataProvider(name = "isSuccessful")
	public Object[][] isSuccessful() {
		return new Object[][] { { CheckOutcome.FUMBLE, false },
				{ CheckOutcome.LUCKY_CHECK, true },
				{ CheckOutcome.SPECTACULAR_FUMBLE, false },
				{ CheckOutcome.SPECTACULAR_SUCCESS, true },
				{ CheckOutcome.SPRUCHHEMMUNG, false },
				{ CheckOutcome.SUCCESSFUL, true },
				{ CheckOutcome.UNSUCCESSFUL, false } };
	}

}
