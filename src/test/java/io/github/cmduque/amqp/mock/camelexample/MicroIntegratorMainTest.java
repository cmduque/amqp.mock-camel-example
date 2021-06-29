package io.github.cmduque.amqp.mock.camelexample;

import io.github.cmduque.amqp.mock.camelexample.routes.CommonRoutesBuilder;
import org.apache.camel.main.Main;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MicroIntegratorMain.class})
public class MicroIntegratorMainTest {

    @Test
    public void mainMustBindBeandLoadSpringRoutesLoadRoutesAndRun() throws Exception {
        Main main = mock(Main.class);
        whenNew(Main.class).withNoArguments().thenReturn(main);
        CommonRoutesBuilder commonRoutesBuilder = mock(CommonRoutesBuilder.class);
        whenNew(CommonRoutesBuilder.class).withNoArguments().thenReturn(commonRoutesBuilder);
        mockStatic(MicroIntegratorMain.class);
        doCallRealMethod().when(MicroIntegratorMain.class);
        MicroIntegratorMain.main();

        MicroIntegratorMain.main();

        verify(main, times(1)).setPropertyPlaceholderLocations("classpath:properties/default.properties");
        verify(main, times(1)).addRoutesBuilder(commonRoutesBuilder);
        verify(main, times(1)).run();
    }
}