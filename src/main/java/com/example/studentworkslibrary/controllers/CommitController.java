package com.example.studentworkslibrary.controllers;

import com.example.studentworkslibrary.POJO.Commit;
import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.services.CommitService;
import com.example.studentworkslibrary.services.FullPathService;
import com.example.studentworkslibrary.util.PathHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/commit/")
@AllArgsConstructor
public class CommitController {

    private final FullPathService fullPathService;
    private final CommitService commitService;

    @GetMapping("{}/{}/allcommits")
    List<Commit> getAllCommits(HttpServletRequest request){
        FullPath fullPath = fullPathService.createFullPath(PathHelper.getEncodeString(request.getRequestURI()));
        return commitService.getAllCommits(fullPath);
    }

    @GetMapping("{}/{}/**")
    public List<Commit> getCommitsByPath(@RequestParam(required = false) Integer unixTime, HttpServletRequest request){
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
