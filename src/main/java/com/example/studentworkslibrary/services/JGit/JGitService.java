package com.example.studentworkslibrary.services.JGit;

import com.example.studentworkslibrary.POJO.Content;
import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.POJO.RepositoryInfo;
import com.example.studentworkslibrary.services.RepositoryInfoFacadeService;
import lombok.AllArgsConstructor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
public class JGitService {

    @Value("${path.to.repositories}")
    private final String REPOSITORIES_PATH;
    private final RepositoryInfoFacadeService repositoryInfoFacadeService;

    private Git getGit(FullPath fullPath) {
        try {
            return Git.open(new File(REPOSITORIES_PATH + "/" + fullPath.getPathToRepository()));
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

    public Content getContent(String username, Map<String, RepositoryInfo> userAndRepositoryInfo, FullPath fullPath){
        try {
            RevCommit commit = repositoryInfoFacadeService.findCommit(username, userAndRepositoryInfo, fullPath);
            return new JGitContent(fullPath, getGit(fullPath), commit).getObject();
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    public GitInfo getGitInfo(FullPath fullPath){
        return new GitInfo(getGit(fullPath));
    }

}
