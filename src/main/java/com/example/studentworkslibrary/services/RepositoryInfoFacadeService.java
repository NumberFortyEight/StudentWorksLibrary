package com.example.studentworkslibrary.services;

import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.POJO.RepositoryInfo;
import lombok.AllArgsConstructor;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class RepositoryInfoFacadeService {
    
    private final NodeCreateService nodeCreateService;
    private final NodeUpdateService nodeUpdateService;
    
    public void processCommitRequest(String username, FullPath fullPath, Map<String, RepositoryInfo> userAndRepositoryInfo, Integer commit) {
        RepositoryInfo repositoryInfoFound = userAndRepositoryInfo.get(username);
        if (repositoryInfoFound == null) {
            RepositoryInfo repositoryInfo = new RepositoryInfo();
            repositoryInfo.setAuthor(fullPath.getAuthor());
            repositoryInfo.setRepositoryName(fullPath.getRepository());
            repositoryInfo.setNode(nodeCreateService.createNodeHierarchy(fullPath));
            userAndRepositoryInfo.put(username, repositoryInfo);
        } else {
            if (repositoryInfoFound.getAuthor().equals(fullPath.getAuthor()) && repositoryInfoFound.getRepositoryName().equals(fullPath.getRepository())) {
                nodeUpdateService.updateNode(repositoryInfoFound.getNode(), fullPath);
            }

        }

    }

    public RevCommit findCommit(String username, Map<String, RepositoryInfo> userAndRepositoryInfo, FullPath fullPath) {
        return null;
    }
}
