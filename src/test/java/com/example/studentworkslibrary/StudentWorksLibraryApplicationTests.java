package com.example.studentworkslibrary;

import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.POJO.Node;
import com.example.studentworkslibrary.services.FullPathService;
import com.example.studentworkslibrary.services.JGit.JGitService;
import com.example.studentworkslibrary.services.node.NodeCreateService;
import com.example.studentworkslibrary.services.node.NodeUpdateService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class StudentWorksLibraryApplicationTests {

    @Autowired
    private FullPathService fullPathService;
    @Autowired
    private NodeUpdateService nodeUpdateService;
    @Autowired
    private NodeCreateService nodeCreateService;
    @Autowired
    private JGitService jGitService;


    @Test
    @SneakyThrows
    void contextLoads() {
        FullPath fullPath = fullPathService.createFullPath("/api/testUser/test.git/file1/file2");
        List<RevCommit> allRevCommits = jGitService.getGitInfo(fullPath).getAllRevCommits();
        RevCommit firstRevCommit = allRevCommits.get(0);
        RevCommit secondRevCommit = allRevCommits.get(1);
        Node nodeHierarchy = nodeCreateService.createNodeHierarchy(fullPath, firstRevCommit);

        FullPath updatedFullPath = fullPathService.createFullPath("/api/testUser/test.git/file2/file1");
        nodeUpdateService.updateNode(nodeHierarchy, updatedFullPath.getWorkPathWithRepository(), secondRevCommit);

        System.out.println(fullPath);
    }

}
