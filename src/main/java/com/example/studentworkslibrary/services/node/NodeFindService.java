package com.example.studentworkslibrary.services.node;

import com.example.studentworkslibrary.POJO.Node;
import com.example.studentworkslibrary.util.PathHelper;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NodeFindService {

    public RevCommit findCommit(Node node, String workPath){
        String name = PathHelper.limit(workPath, 1);
        boolean isActualName = node.getName().equals(name);
        if (isActualName) {
            String childName = PathHelper.skipAndLimit(workPath, 1, 1);
            if (!childName.equals("")) {
                Optional<Node> optionalChildNode = findChildNode(node, childName);
                if (optionalChildNode.isPresent()) {
                    return findCommit(optionalChildNode.get(), PathHelper.skip(workPath, 1));
                }
            }
        }
        return node.getRevCommit();
    }

    public Optional<Node> findChildNode(Node node, String childNodeName){
        return node.getChildNodes()
                .stream()
                .filter(childNodes -> childNodes.getName().equals(childNodeName))
                .findFirst();
    }
}
