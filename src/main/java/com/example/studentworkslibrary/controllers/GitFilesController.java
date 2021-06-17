package com.example.studentworkslibrary.controllers;

import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.POJO.RepositoryInfo;
import com.example.studentworkslibrary.services.FullPathService;
import com.example.studentworkslibrary.services.JGit.JGitService;
import com.example.studentworkslibrary.services.RepositoryInfoFacadeService;
import com.example.studentworkslibrary.util.PathHelper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class  GitFilesController {
    @NonNull
    private final FullPathService fullPathService;
    @NonNull
    private final RepositoryInfoFacadeService repositoryInfoFacadeService;
    @NonNull
    private final JGitService jGitService;

    private final Map<String, RepositoryInfo> userAndRepositoryInfo = new HashMap<>();

    @GetMapping("{}/{}/**")
    public Object gitApi(HttpServletRequest request, @RequestParam(required = false) Integer commit){
        String username = "username";
        FullPath fullPath = fullPathService.createFullPath(PathHelper.getEncodeString(request.getRequestURI()));
        if (commit != null) {
            repositoryInfoFacadeService.processCommitRequest(username, fullPath, userAndRepositoryInfo, commit);
        }
        RevCommit revCommit = repositoryInfoFacadeService.findCommit(username, userAndRepositoryInfo, fullPath);
        return jGitService.getContent(fullPath, revCommit).getContent();
    }

}
