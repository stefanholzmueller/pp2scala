package stefanholzmueller.pp2.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckOutcomeTest {

	@Test(dataProvider = "isSuccessful")
	public void isSuccessful(OutcomeEnum checkSuccess, boolean expected)
			throws Exception {
		assertThat(checkSuccess.isSuccessful(), is(expected));
	}

	@DataProvider(name = "isSuccessful")
	public Object[][] isSuccessful() {
		return new Object[][] { { OutcomeEnum.FUMBLE, false },
				{ OutcomeEnum.LUCKY_CHECK, true },
				{ OutcomeEnum.SPECTACULAR_FUMBLE, false },
				{ OutcomeEnum.SPECTACULAR_SUCCESS, true },
				{ OutcomeEnum.SPRUCHHEMMUNG, false },
				{ OutcomeEnum.SUCCESSFUL, true },
				{ OutcomeEnum.UNSUCCESSFUL, false } };
	}

}
