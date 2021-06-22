package com.example.studentworkslibrary.services.node;

import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.POJO.Node;
import com.example.studentworkslibrary.util.PathHelper;
import lombok.AllArgsConstructor;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class NodeUpdateService {

    private final NodeCreateService nodeCreateService;
    private final NodeFindService nodeFindService;

    public void updateNode(Node node, String workPathWithRepository, RevCommit actualRevCommit) {
        String[] pathNuggets = workPathWithRepository.split("/");
        if (node.getName().equals(pathNuggets[0])) {

            if (pathNuggets.length > 1) {
                Optional<Node> childNode = nodeFindService.findChildNode(node, pathNuggets[1]);
                String nextWorkPath = PathHelper.skip(workPathWithRepository, 1);

                if (childNode.isEmpty()) {
                    nodeCreateService.createNodeBranch(node, nextWorkPath, actualRevCommit);
                } else {
                    updateNode(childNode.get(), nextWorkPath, actualRevCommit);
                }
                return;
            }
            node.setRevCommit(actualRevCommit);
            node.getChildNodes().clear();
        }
    }
}
