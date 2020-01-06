package com.food4good.controllers;

import com.food4good.dto.SuplierDTO;
import com.food4good.dto.SuplierInfoDTO;
import com.food4good.facad.SuplierFacad;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class SupliersControllerTest {

    @Mock
    SuplierFacad suplierFacad;

    SupliersController sut;
    SuplierDTO suplierDTO;
    @Mock
    List<SuplierInfoDTO> resultList;
    @Before
    public void initTest() {
        sut = new SupliersController(suplierFacad);
        suplierDTO = new SuplierDTO();
    }

    @Test
    public void suplierById_getCorrectSupplierWhenIdProvided() throws Exception {

        Mockito.when(suplierFacad.getById(100L)).thenReturn(suplierDTO);
        SuplierDTO result = sut.suplierById(100L);
        Assert.assertEquals(result, suplierDTO);
    }

    @Test
    public void suplierById_getEmptyWhenSupplierNotFound() throws Exception {

        SuplierDTO result = sut.suplierById(100L);
        Assert.assertEquals(result, null);
    }
    @Test(expected=Exception.class)
    public void suplierById_getErrorWhenSupplierIdIsNull() throws Exception {
        SuplierDTO result = sut.suplierById(null);
    }

    @Test
    public void getAllSupliersInfo_infoExist() throws Exception {
        Mockito.when(suplierFacad.getAllInfo()).thenReturn(resultList);
        List<SuplierInfoDTO> result = sut.getAllSupliersInfo();
        Assert.assertEquals(result, resultList);
    }
}