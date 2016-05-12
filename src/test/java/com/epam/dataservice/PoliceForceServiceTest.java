package com.epam.dataservice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PoliceForceServiceTest {

    private PoliceForceService service;

    @Before
    public void init(){
        service = new PoliceForceService();
    }

    @Test
    public void givenExistingPoliceForceNameThenShouldReturnCorrectContactNo(){
        String policeForceNameOfLeicestershire = "Leicestershire";
        String expectedContactNoOfLeicestershire = "1316386233";

        assertThat(service.getContactNo(policeForceNameOfLeicestershire)).isEqualTo(expectedContactNoOfLeicestershire);
    }

    @Test
    public void givenUnknownPoliceForceNameThenShouldReturnPrefixOfContactNo(){
        String mockPoliceForceName = "mockUnknownName";
        String expectedContactNo = "13163862";

        assertThat(service.getContactNo(mockPoliceForceName)).isEqualTo(expectedContactNo);
    }
}
