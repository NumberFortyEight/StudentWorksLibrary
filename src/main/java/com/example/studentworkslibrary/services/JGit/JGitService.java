package com.example.studentworkslibrary.services.JGit;

import com.example.studentworkslibrary.POJO.ObjectWithName;
import com.example.studentworkslibrary.POJO.FullPath;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class JGitService {

    @Value("${path.to.repositories}")
    private String REPOSITORIES_PATH;

    private Git getGit(FullPath fullPath) {
        try {
            return Git.open(new File(REPOSITORIES_PATH + "/" + fullPath.getPathToRepository()));
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

    public ObjectWithName getObject(FullPath fullPath, RevCommit commit){
        try {
            return new JGitObject(fullPath, getGit(fullPath), commit).getObject();
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    public GitInfo getGitInfo(FullPath fullPath){
        return new GitInfo(getGit(fullPath));
    }

}
