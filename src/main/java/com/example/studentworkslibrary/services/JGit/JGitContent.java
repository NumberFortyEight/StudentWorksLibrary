package com.example.studentworkslibrary.services.JGit;

import com.example.studentworkslibrary.POJO.Content;
import com.example.studentworkslibrary.POJO.FileFactory;
import com.example.studentworkslibrary.POJO.FileModel;
import com.example.studentworkslibrary.POJO.FullPath;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JGitContent {

    private final Repository repository;
    private final RevCommit currentCommit;
    private final TreeWalk treeWalk;
    private final String workPath;
    private final String pathToRepository;

    public JGitContent(FullPath fullPath, Git git, RevCommit revCommit) {
        this.repository = git.getRepository();
        this.currentCommit = revCommit;
        this.treeWalk = new TreeWalk(repository);
        this.workPath = fullPath.getWorkPath();
        this.pathToRepository = fullPath.getAuthor() + "/" + fullPath.getRepository();
    }

    private boolean isThisExist() throws IOException {
        if (workPath.equals("") || workPath.equals("/")) {
            return true;
        }
        treeWalk.addTree(currentCommit.getTree());
        treeWalk.setRecursive(false);
        while (treeWalk.next()) {
            if (treeWalk.getPathString().equals(workPath)) {
                return true;
            }
            if (treeWalk.isSubtree()) {
                treeWalk.enterSubtree();
            }
        }
        treeWalk.reset();
        return false;
    }

    private boolean isFile() throws Exception {
        if (workPath.equals("") || workPath.equals("/")) {
            treeWalk.reset();
            return false;
        }
        treeWalk.addTree(currentCommit.getTree());
        treeWalk.setRecursive(false);
        while (treeWalk.next()) {
            if (treeWalk.isSubtree()) {
                if (treeWalk.getPathString().equals(workPath)) {
                    treeWalk.reset();
                    return false;
                }
                treeWalk.enterSubtree();
            } else {
                if (treeWalk.getPathString().equals(workPath)) {
                    treeWalk.reset();
                    return true;
                }
            }
        }
        throw new IllegalStateException("unknown state");
    }

    public Content getObject() throws Exception {
        if (isThisExist()) {
            if (isFile()) {
                return loadFile();
            } else {
                return getDirs();
            }
        }
        throw new IllegalStateException("emptyContent");
    }

    @SuppressWarnings("LoopStatementThatDoesntLoop")
    private Content loadFile() throws IOException {
        treeWalk.addTree(currentCommit.getTree());
        treeWalk.setRecursive(true);
        treeWalk.setFilter(PathFilter.create(workPath));
        try (ObjectReader objectReader = repository.newObjectReader()) {
            while (treeWalk.next()) {
                return new Content(treeWalk.getNameString(), objectReader.open(treeWalk.getObjectId(0)).getBytes());
            }
        }
        return null;
    }

    private Content getDirs() throws IOException {
        treeWalk.addTree(currentCommit.getTree());
        treeWalk.setRecursive(false);

        String[] split = workPath.split("/");
        List<FileModel> toLoad = new ArrayList<>();

        if (workPath.equals("")) {
            while (treeWalk.next()) {
                toLoad.add(FileFactory.createFileModel(
                        treeWalk.isSubtree(),
                        treeWalk.getNameString(),
                        pathToRepository,
                        currentCommit.getCommitTime(),
                        treeWalk.getPathString()));
            }
        } else {
            boolean skip = true;
            int i = 0;
            while (skip && treeWalk.next()) {
                if (treeWalk.isSubtree() && treeWalk.getNameString().equals(split[i])) {
                    treeWalk.enterSubtree();
                    i++;
                    if (treeWalk.getPathString().equals(workPath)) {
                        skip = false;
                    }
                }
            }
            while (treeWalk.next()) {
                if (treeWalk.getPathString().split("/").length > split.length) {
                    toLoad.add(FileFactory.createFileModel(
                            treeWalk.isSubtree(),
                            treeWalk.getNameString(),
                            pathToRepository,
                            currentCommit.getCommitTime(),
                            treeWalk.getPathString()));
                }
            }
        }
        return new Content(".json", toLoad);
    }


}
