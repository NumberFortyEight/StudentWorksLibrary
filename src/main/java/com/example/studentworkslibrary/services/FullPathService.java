package com.example.studentworkslibrary.services;

import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.util.PathHelper;
import org.springframework.stereotype.Service;

@Service
public class FullPathService {

    public FullPath createFullPath(String fullPath){
        FullPath fullPathObject = new FullPath();
        fullPathObject.setPathToRepository(PathHelper.getAbsolutePath(PathHelper.skipAndLimit(fullPath, 1, 2)));
        fullPathObject.setAuthor(PathHelper.getAbsolutePath(PathHelper.skipAndLimit(fullPath, 1, 1)));
        fullPathObject.setRepository(PathHelper.getRelativePath(PathHelper.skipAndLimit(fullPath, 2, 1)));
        fullPathObject.setWorkPath(PathHelper.getRelativePath(PathHelper.skip(fullPath, 3)));
        fullPathObject.setWorkPathWithRepository(PathHelper.getRelativePath(PathHelper.skip(fullPath, 2)));
        return fullPathObject;
    }
}
