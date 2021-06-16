package com.example.studentworkslibrary.services;

import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.POJO.RepositoryInfo;
import com.example.studentworkslibrary.services.JGit.GitInfo;
import com.example.studentworkslibrary.services.JGit.JGitService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

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
        RevCommit commitByTime = jGitService.getGitInfo(fullPath).getCommitByTime(commitUnixTime);
        if (repositoryInfoFound == null) {
            RepositoryInfo repositoryInfo = new RepositoryInfo();
            repositoryInfo.setAuthor(fullPath.getAuthor());
            repositoryInfo.setRepositoryName(fullPath.getRepository());
            repositoryInfo.setNode(nodeCreateService.createNodeHierarchy(fullPath, commitByTime));
            userAndRepositoryInfo.put(username, repositoryInfo);
        } else {
            if (repositoryInfoFound.getAuthor().equals(fullPath.getAuthor()) && repositoryInfoFound.getRepositoryName().equals(fullPath.getRepository())) {
                nodeUpdateService.updateNode(repositoryInfoFound.getNode(), fullPath);
            }
        }

    }
    // TODO: 16.06.2021  //delete f sneaky
    @SneakyThrows
    public RevCommit findCommit(String username, Map<String, RepositoryInfo> userAndRepositoryInfo, FullPath fullPath) {
        RepositoryInfo repositoryInfo = userAndRepositoryInfo.get(username);
        if (repositoryInfo != null) {
            String author = repositoryInfo.getAuthor();
            String repositoryName = repositoryInfo.getRepositoryName();
            boolean isCurrentRepository = fullPath.getAuthor().equals(author) && fullPath.getRepository().equals(repositoryName);
            if (isCurrentRepository) {
                return nodeFindService.findCommit(repositoryInfo.getNode(), fullPath.getWorkPath());
            }
        }
        return jGitService.getGitInfo(fullPath).getLastCommit();
    }
}
