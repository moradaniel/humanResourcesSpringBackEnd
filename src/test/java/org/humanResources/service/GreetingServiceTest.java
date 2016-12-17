package org.humanResources.service;

import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class GreetingServiceTest	{
    GreetingService	service;

    @Before
    public void setup()	{
        service = new GreetingService();
    }

    @Test
    public	void testGreet()	{
        assertThat("Hello Test").isEqualTo(service.greet("Test"));
    }
}