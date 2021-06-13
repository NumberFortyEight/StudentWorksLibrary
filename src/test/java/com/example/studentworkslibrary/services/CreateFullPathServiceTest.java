package com.example.studentworkslibrary.services;


import com.example.studentworkslibrary.POJO.FullPath;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

public class CreateFullPathServiceTest {

    CreateFullPathService createFullPathService = new CreateFullPathService();

    @Test
    public void testFullPath(){
        FullPath fullPath = createFullPathService.createFullPath("api/dima/.git/sds/sdsd/asdasd/asdasd/asdasdsa");
        Assertions.assertEquals(fullPath.getAuthor(), "dima");
        Assertions.assertEquals(fullPath.getRepository(), ".git");
    }
}