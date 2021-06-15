package com.example.studentworkslibrary.services.JGit;

import com.example.studentworkslibrary.POJO.FullPath;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public class GitInfo {

    private final Git git;

    public GitInfo(Git git) {
        this.git = git;
    }

    public RevCommit getCommitByTime(int unixTime) throws GitAPIException {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(git.log()
                        .call()
                        .iterator(), Spliterator.ORDERED), false)
                .filter(revCommit -> revCommit.getCommitTime() == unixTime)
                .findFirst()
                .orElse(getLastCommit());
    }

    public RevCommit getLastCommit() throws GitAPIException {
        return git.log().call().iterator().next();
    }

    public List<RevCommit> getAllCommits() throws GitAPIException {
        List<RevCommit> revCommitList = new ArrayList<>();
        for (RevCommit revCommit : git.log().call()) {
            revCommitList.add(revCommit);
        }
        return revCommitList;
    }

    public List<RevCommit> getCommitsByFullPath(FullPath fullPath) throws Exception {
        String workPath = fullPath.getWorkPath();
        if (workPath.equals("") || workPath.equals("/")) {
            return getAllCommits();
        } else {
            return getCommitsByPath(workPath);
        }
    }

    private ArrayList<RevCommit> getCommitsByPath(String path) throws Exception {
        ArrayList<RevCommit> commits = new ArrayList<>();
        RevCommit start = null;
        do {
            Iterable<RevCommit> log = git.log().addPath(path).call();
            for (RevCommit commit : log) {
                if (commits.contains(commit)) {
                    start = null;
                } else {
                    start = commit;
                    commits.add(commit);
                }
            }
            if (start == null) return commits;
        }
        while ((path = getRenamedPath(start, path)) != null);

        return commits;
    }

    private String getRenamedPath( RevCommit start, String path) throws Exception {
        Iterable<RevCommit> allCommitsLater = git.log().add(start).call();
        for (RevCommit commit : allCommitsLater) {
            Repository repository = git.getRepository();
            TreeWalk tw = new TreeWalk(repository);
            tw.addTree(commit.getTree());
            tw.addTree(start.getTree());
            tw.setRecursive(true);
            RenameDetector rd = new RenameDetector(repository);
            rd.addAll(DiffEntry.scan(tw));
            List<DiffEntry> files = rd.compute();
            for (DiffEntry diffEntry : files) {
                if ((diffEntry.getChangeType() == DiffEntry.ChangeType.RENAME || diffEntry.getChangeType() == DiffEntry.ChangeType.COPY) && diffEntry.getNewPath().contains(path)) {
                    return diffEntry.getOldPath();
                }
            }
        }
        return null;
    }

}
