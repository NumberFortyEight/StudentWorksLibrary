package com.example.studentworkslibrary.services;

import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.POJO.Node;
import com.example.studentworkslibrary.util.PathHelper;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.stereotype.Service;

@Service
public class NodeCreateService {

    public Node createNodeHierarchy(FullPath fullPath, RevCommit revCommit){
        Node repositoryNode = new Node();
        repositoryNode.setName(fullPath.getRepository());
        repositoryNode.setRevCommit(revCommit);
        return createNodeBranch(repositoryNode, fullPath.getWorkPath(), revCommit);
    }

    private Node createNodeBranch(Node lastNode, String workPath, RevCommit revCommit) {
        String[] pathNuggets = workPath.split("/");
        Node node = new Node();
        node.setName(pathNuggets[0]);
        node.setRevCommit(revCommit);
        lastNode.getChildNodes().add(node);
        if (pathNuggets.length > 1) {
            createNodeBranch(node, PathHelper.skip(workPath, 1), revCommit);
        }
        return node;
    }
}
