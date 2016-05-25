package com.epam.dataservice;


import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Created by rahul.mujnani on 5/5/2016.
**/

public class PoliceServiceTest {

    private static PoliceForceService policeForceServiceTest= new PoliceForceService();

    @BeforeClass
    public static void init() {

    }

    @Test
    public void test_policeForce_Contact() {
        String NO_DATA_ONLY_PREFIX="13163862";
        assertEquals("131638621",policeForceServiceTest.getContactNo("Metropolitan Police"));
        assertEquals("1316386214",policeForceServiceTest.getContactNo("South Yorkshire"));
        assertEquals(NO_DATA_ONLY_PREFIX,policeForceServiceTest.getContactNo("InvalidPoliceForceName"));
    }
}


