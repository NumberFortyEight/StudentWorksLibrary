package com.example.studentworkslibrary.controllers;

import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.POJO.RepositoryInfo;
import com.example.studentworkslibrary.services.CreateFullPathService;
import com.example.studentworkslibrary.services.JGit.JGitContent;
import com.example.studentworkslibrary.services.JGit.JGitService;
import com.example.studentworkslibrary.services.RepositoryInfoFacadeService;
import com.example.studentworkslibrary.util.PathHelper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GitFilesController {
    @NonNull
    private final CreateFullPathService createFullPathService;
    @NonNull
    private final RepositoryInfoFacadeService repositoryInfoFacadeService;
    @NonNull
    private final JGitService jGitService;

    private final Map<String, RepositoryInfo> userAndRepositoryInfo = new HashMap<>();

    @GetMapping("/**")
    public File gitApi(HttpServletRequest request, @RequestParam(required = false) Integer commit){
        String username = "username";
        FullPath fullPath = createFullPathService.createFullPath(PathHelper.getEncodeString(request.getRequestURI()));
        if (commit != null) {
            repositoryInfoFacadeService.processCommitRequest(username, fullPath, userAndRepositoryInfo, commit);
        }



        return null;
    }

}
