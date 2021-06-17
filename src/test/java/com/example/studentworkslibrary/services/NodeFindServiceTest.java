package com.example.studentworkslibrary.services;

import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.services.JGit.GitInfo;
import org.eclipse.jgit.api.Git;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class NodeFindServiceTest {


    @Test
    public void findCommit() {
        FullPath fullPath = new FullPath();
        fullPath.setAuthor("TESTED");
        fullPath.setRepository(".git");

    }
}