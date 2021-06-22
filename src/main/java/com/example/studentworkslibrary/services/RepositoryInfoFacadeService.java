package com.example.studentworkslibrary.services;

import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.POJO.RepositoryInfo;
import com.example.studentworkslibrary.services.JGit.JGitService;
import com.example.studentworkslibrary.services.node.NodeCreateService;
import com.example.studentworkslibrary.services.node.NodeFindService;
import com.example.studentworkslibrary.services.node.NodeUpdateService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class RepositoryInfoFacadeService {

    private final JGitService jGitService;
    private final NodeCreateService nodeCreateService;
    private final NodeUpdateService nodeUpdateService;
    private final NodeFindService nodeFindService;

    @SneakyThrows
    public void processCommitRequest(String username, FullPath fullPath, Map<String, RepositoryInfo> userAndRepositoryInfo, Integer commitUnixTime) {
        RepositoryInfo repositoryInfoFound = userAndRepositoryInfo.get(username);
        RevCommit commitByTime = jGitService.getGitInfo(fullPath).getRevCommitByTime(commitUnixTime);
        if (repositoryInfoFound != null) {
            if (isCurrentRepository(repositoryInfoFound, fullPath)) {
                nodeUpdateService.updateNode(repositoryInfoFound.getNode(), fullPath.getWorkPathWithRepository(), commitByTime);
                return;
            }
        }
        RepositoryInfo repositoryInfo = new RepositoryInfo();
        repositoryInfo.setAuthor(fullPath.getAuthor());
        repositoryInfo.setRepositoryName(fullPath.getRepository());
        repositoryInfo.setNode(nodeCreateService.createNodeHierarchy(fullPath, commitByTime));
        userAndRepositoryInfo.put(username, repositoryInfo);
    }

    // TODO: 16.06.2021  //delete f sneaky
    @SneakyThrows
    public RevCommit findCommit(String username, Map<String, RepositoryInfo> userAndRepositoryInfo, FullPath fullPath) {
        RepositoryInfo repositoryInfo = userAndRepositoryInfo.get(username);
        if (repositoryInfo != null) {
            if (isCurrentRepository(repositoryInfo, fullPath)) {
                return nodeFindService.findCommit(repositoryInfo.getNode(), fullPath.getWorkPathWithRepository());
            }
        }
        return jGitService.getGitInfo(fullPath).getLastRevCommit();
    }

    boolean isCurrentRepository(RepositoryInfo repositoryInfo, FullPath fullPath){
        return repositoryInfo.getAuthor().equals(fullPath.getAuthor()) && repositoryInfo.getRepositoryName().equals(fullPath.getRepository());
    }
}
