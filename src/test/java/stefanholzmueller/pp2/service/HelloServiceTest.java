package stefanholzmueller.pp2.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import stefanholzmueller.pp2.service.HelloService;
import stefanholzmueller.pp2.service.RandomService;

@Test
public class HelloServiceTest {

    private HelloService helloService;
    @Mock
    private RandomService randomServiceMock;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        helloService = new HelloService(randomServiceMock);
    }

    public void mockDependency() {
        when(randomServiceMock.getRandomDice(anyInt())).thenReturn(123);

        String sayHello = helloService.sayHello(0);

        assertThat(sayHello, is("hallo 123"));
    }
}
