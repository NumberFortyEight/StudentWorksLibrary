package com.example.studentworkslibrary.services;

import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.POJO.Node;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class NodeCreateServiceTest {

    private final NodeCreateService nodeCreateService = new NodeCreateService();

    @Test
    public void createNodeHierarchy() {
        FullPath fullPath = new FullPath();
        fullPath.setWorkPath("11/cxx/man");
        fullPath.setAuthor("miro");
        fullPath.setRepository("fgd.git");

        Node node = nodeCreateService.createNodeHierarchy(fullPath);
        Assert.isTrue(node.getName().equals("11"));
    }
}