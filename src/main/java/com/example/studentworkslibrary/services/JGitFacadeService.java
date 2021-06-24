package com.example.studentworkslibrary.services;

import com.example.studentworkslibrary.POJO.Content;
import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.POJO.ObjectWithName;
import com.example.studentworkslibrary.POJO.RepositoryInfo;
import com.example.studentworkslibrary.services.JGit.JGitService;
import lombok.AllArgsConstructor;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class JGitFacadeService {

    private final RepositoryInfoFacadeService repositoryInfoFacadeService;
    private final JGitService jGitService;

    public Content getContent(FullPath fullPath, String username, Map<String, RepositoryInfo> userAndRepositoryInfo){
        RevCommit revCommit = repositoryInfoFacadeService.findCommit(username, userAndRepositoryInfo, fullPath);
        ObjectWithName objectWithName = jGitService.getObject(fullPath, revCommit);
        return new Content(findContentType(objectWithName.getName()), objectWithName.getObject());
    }

    private String findContentType(String objectName) {
        if (objectName.endsWith(".jpg") || objectName.endsWith(".bmp") || objectName.endsWith(".tiff")) {
            return MediaType.IMAGE_JPEG_VALUE;
        }
        if (objectName.endsWith(".png")){
            return MediaType.IMAGE_PNG_VALUE;
        }
        if (objectName.endsWith(".gif")) {
            return MediaType.IMAGE_GIF_VALUE;
        }
        if (objectName.endsWith(".svg")) {
            return "image/svg+xml";
        }
        if (objectName.endsWith(".avi") || objectName.endsWith(".mp4") || objectName.endsWith(".wmf")) {
            return "video/mp4";
        }
        if (objectName.endsWith(".mp3") || objectName.endsWith(".wma") || objectName.endsWith(".wav")) {
            return "audio/wav";
        }
        return null;
    }

}
