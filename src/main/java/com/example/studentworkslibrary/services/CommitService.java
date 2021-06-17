package com.example.studentworkslibrary.services;

import com.example.studentworkslibrary.POJO.Commit;
import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.services.JGit.JGitService;
import lombok.AllArgsConstructor;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommitService {

    private final JGitService jGitService;

    public List<Commit> getAllCommits(FullPath fullPath) {
        try {
            return jGitService.getGitInfo(fullPath)
                    .getAllRevCommits()
                    .stream()
                    .map(revCommit -> new Commit(revCommit.getFullMessage(), revCommit.getAuthorIdent().getName(), String.valueOf(revCommit.getCommitTime())))
                    .collect(Collectors.toList());
        } catch (GitAPIException e) {
            throw new IllegalStateException();
        }
    }

    public List<Commit> getCommitsByPath(FullPath fullPath){
        try {
            return jGitService.getGitInfo(fullPath)
                    .getRevCommitsByFullPath(fullPath)
                    .stream().map(revCommit -> new Commit(revCommit.getFullMessage(), revCommit.getAuthorIdent().getName(), String.valueOf(revCommit.getCommitTime())))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }
}
