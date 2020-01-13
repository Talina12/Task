package com.food4good.controllers;

import com.food4good.dto.supplierDTO;
import com.food4good.dto.supplierInfoDTO;
import com.food4good.facad.supplierFacad;
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
    supplierFacad supplierFacad;

    suppliersController sut;
    supplierDTO supplierDTO;
    @Mock
    List<supplierInfoDTO> resultList;
    @Before
    public void initTest() {
        sut = new suppliersController(supplierFacad);
        supplierDTO = new supplierDTO();
    }

    @Test
    public void supplierById_getCorrectSupplierWhenIdProvided() throws Exception {

        Mockito.when(supplierFacad.getById(100L)).thenReturn(supplierDTO);
        supplierDTO result = sut.supplierById(100L);
        Assert.assertEquals(result, supplierDTO);
    }

    @Test
    public void supplierById_getEmptyWhenSupplierNotFound() throws Exception {

        supplierDTO result = sut.supplierById(100L);
        Assert.assertEquals(result, null);
    }
    @Test(expected=Exception.class)
    public void supplierById_getErrorWhenSupplierIdIsNull() throws Exception {
        supplierDTO result = sut.supplierById(null);
    }

    @Test
    public void getAllsuppliersInfo_infoExist() throws Exception {
        Mockito.when(supplierFacad.getAllInfo()).thenReturn(resultList);
        List<supplierInfoDTO> result = sut.getAllsuppliersInfo();
        Assert.assertEquals(result, resultList);
    }
}