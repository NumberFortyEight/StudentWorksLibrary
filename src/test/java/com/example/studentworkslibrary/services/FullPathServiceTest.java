package com.example.studentworkslibrary.services;


import com.example.studentworkslibrary.POJO.FullPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FullPathServiceTest {

    FullPathService fullPathService = new FullPathService();

    @Test
    public void testFullPath(){
        FullPath fullPath = fullPathService.createFullPath("api/dima/.git/sds/sdsd/asdasd/asdasd/asdasdsa");
        Assertions.assertEquals(fullPath.getAuthor(), "/dima");
        Assertions.assertEquals(fullPath.getRepository(), ".git");
    }
}