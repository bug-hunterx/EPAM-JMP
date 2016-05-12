package com.epam.dataservice;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Nick on 04.05.2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class PoliceForceServiceTest extends TestCase {

    private PoliceForceService policeForceService;

    @Before
    public void setUp() throws Exception {
        policeForceService = new PoliceForceService();
        policeForceService.init();

    }

    @Test
    public void testGetContactNoExistingLabel() throws Exception {

        String number = policeForceService.getContactNo("Nottinghamshire");
        assertEquals(number, "1316386231");
    }

    @Test
    public void testGetContactNoNotExistingLabel() throws Exception {

        String number = policeForceService.getContactNo("Foobar");
        assertEquals(number, "13163862");
    }

}