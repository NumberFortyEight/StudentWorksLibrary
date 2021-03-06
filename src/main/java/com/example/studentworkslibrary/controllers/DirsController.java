package com.example.studentworkslibrary.controllers;

import com.example.studentworkslibrary.POJO.FileModel;
import com.example.studentworkslibrary.services.DirsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class DirsController {

    @Value("${path.to.repositories}")
    public String REPOSITORIES_PATH;

    private final DirsService dirsService;

    @GetMapping(value = { "api", "api/{student}"})
    public List<FileModel> getDirectory(@PathVariable Optional<String> student) {
        String url = student.map(existStudent -> "/" + existStudent).orElse("/");
        return dirsService.getFileModelList(REPOSITORIES_PATH, url).orElse(List.of());
    }

    @RequestMapping("/")
    void handleFoo(HttpServletResponse response) throws IOException {
        response.sendRedirect("/build/index.html");
    }
}