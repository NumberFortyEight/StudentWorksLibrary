package com.example.studentworkslibrary.controllers;

import com.example.studentworkslibrary.POJO.Content;
import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.POJO.RepositoryInfo;
import com.example.studentworkslibrary.services.FullPathService;
import com.example.studentworkslibrary.services.JGit.JGitService;
import com.example.studentworkslibrary.services.JGitFacadeService;
import com.example.studentworkslibrary.services.RepositoryInfoFacadeService;
import com.example.studentworkslibrary.util.PathHelper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
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
    private final JGitFacadeService jGitFacadeService;

    private final Map<String, RepositoryInfo> userAndRepositoryInfo = new HashMap<>();

    @SneakyThrows
    @GetMapping("{student}/{repository}/**")
    public Object gitApi(HttpServletRequest request, @RequestParam(required = false) Integer commit, HttpServletResponse response){
        String username = "username";
        FullPath fullPath = fullPathService.createFullPath(PathHelper.getEncodeString(request.getRequestURI()));
        if (commit != null) {
            repositoryInfoFacadeService.processCommitRequest(username, fullPath, userAndRepositoryInfo, commit);
        }
        Content content = jGitFacadeService.getContent(fullPath, username, userAndRepositoryInfo);
        if (content.getContentType() != null) {
            response.setContentType(content.getContentType());
            IOUtils.copy(new ByteArrayInputStream((byte[]) content.getObject()), response.getOutputStream());
            return null;
        } else {
            return content.getObject();
        }
    }

}
