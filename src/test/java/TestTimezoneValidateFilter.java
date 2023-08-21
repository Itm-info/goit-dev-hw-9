import itm.HttpWorks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestTimezoneValidateFilter {
    @Test
    public void testTimezoneValidateFilterOnAbsentParamWorksOk() throws IOException {
        int actual = HttpWorks.httpGetResponseCode("http://localhost:8080/hw/time-templated");
        int expected = 200;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testTimezoneValidateFilterOnValidParamUCTEncodedPlusNumberWorksOk() throws IOException {
        int actual = HttpWorks.httpGetResponseCode("http://localhost:8080/hw/time-templated?timezone=UTC%2B2");
        int expected = 200;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testTimezoneValidateFilterOnValidParamNameWorksOk() throws IOException {
        int actual = HttpWorks.httpGetResponseCode("http://localhost:8080/hw/time-templated?timezone=Europe/Kyiv");
        int expected = 200;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testTimezoneValidateFilterOnInvalidParamWorksOk() throws IOException {
        int actual = HttpWorks.httpGetResponseCode("http://localhost:8080/hw/time-templated?timezone=UTC-10000");
        int expected = 400;
        Assertions.assertEquals(expected, actual);
    }

}
