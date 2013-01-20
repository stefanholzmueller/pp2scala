package stefanholzmueller.pp2.checks;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckSuccessTest {

	@Test(dataProvider = "isSuccessful")
	public void isSuccessful(CheckOutcome checkSuccess, boolean expected) throws Exception {
		assertThat(checkSuccess.isSuccessful(), is(expected));
	}

	@DataProvider(name = "isSuccessful")
	public Object[][] isSuccessful() {
		return new Object[][] { { CheckOutcome.FUMBLE, false }, { CheckOutcome.LUCKY_CHECK, true },
				{ CheckOutcome.SPECTACULAR_FUMBLE, false }, { CheckOutcome.SPECTACULAR_SUCCESS, true },
				{ CheckOutcome.SPRUCHHEMMUNG, false }, { CheckOutcome.SUCCESSFUL, true },
				{ CheckOutcome.UNSUCCESSFUL, false } };
	}

	@Test(dataProvider = "isFumble")
	public void isFumble(CheckOutcome checkSuccess, boolean expected) throws Exception {
		assertThat(checkSuccess.isFumble(), is(expected));
	}

	@DataProvider(name = "isFumble")
	public Object[][] isFumble() {
		return new Object[][] { { CheckOutcome.FUMBLE, true }, { CheckOutcome.LUCKY_CHECK, false },
				{ CheckOutcome.SPECTACULAR_FUMBLE, true }, { CheckOutcome.SPECTACULAR_SUCCESS, false },
				{ CheckOutcome.SPRUCHHEMMUNG, false }, { CheckOutcome.SUCCESSFUL, false },
				{ CheckOutcome.UNSUCCESSFUL, false } };
	}
}
