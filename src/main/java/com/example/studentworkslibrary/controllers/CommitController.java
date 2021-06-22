package com.example.studentworkslibrary.controllers;

import com.example.studentworkslibrary.POJO.Commit;
import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.services.CommitService;
import com.example.studentworkslibrary.services.FullPathService;
import com.example.studentworkslibrary.util.PathHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/commit/")
public class CommitController {

    private final FullPathService fullPathService;
    private final CommitService commitService;

    @GetMapping("{student}/{repository}/allcommits")
    public List<Commit> getAllCommits(HttpServletRequest request, @PathVariable String student, @PathVariable String repository){
        FullPath fullPath = fullPathService.createFullPath(PathHelper.getEncodeString(request.getRequestURI()));
        return commitService.getAllCommits(fullPath);
    }

    @GetMapping("{student}/{repository}/**")
    public List<Commit> getCommitsByPath(@RequestParam(required = false) Integer unixTime, HttpServletRequest request, @PathVariable String repository, @PathVariable String student){
        FullPath fullPath = fullPathService.createFullPath(PathHelper.getEncodeString(request.getRequestURI()));
        List<Commit> commitsByPath = commitService.getCommitsByPath(fullPath);
        if (unixTime == null){
            return commitsByPath;
        } else {
            return commitsByPath.stream()
                    .filter(commit -> Integer.parseInt(commit.getSimpleDateFormat()) <= unixTime)
                    .collect(Collectors.toList());
        }
    }
}

