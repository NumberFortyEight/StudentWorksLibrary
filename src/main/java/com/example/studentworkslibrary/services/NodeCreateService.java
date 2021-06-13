package com.example.studentworkslibrary.services;

import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.POJO.Node;
import com.example.studentworkslibrary.util.PathHelper;
import org.springframework.stereotype.Service;

@Service
public class NodeCreateService {

    public Node createNodeHierarchy(FullPath fullPath){
        Node repositoryNode = new Node();
        repositoryNode.setName(fullPath.getRepository());
        return createNodeBranch(repositoryNode, fullPath.getWorkPath());
    }

    private Node createNodeBranch(Node lastNode, String workPath) {
        String[] pathNuggets = workPath.split("/");
        Node node = new Node();
        node.setName(pathNuggets[0]);
        lastNode.getChildNodes().add(node);
        if (pathNuggets.length > 1) {
            createNodeBranch(node, PathHelper.skip(workPath, 1));
        }
        return node;
    }
}
