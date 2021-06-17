package com.example.studentworkslibrary.services;

import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.POJO.Node;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class NodeCreateServiceTest {


    @Test
    public void createNodeHierarchy() {
        FullPath fullPath = new FullPath();
        fullPath.setWorkPath("11/cxx/man");
        fullPath.setAuthor("miro");
        fullPath.setRepository("fgd.git");
    }
}