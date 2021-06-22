package com.example.studentworkslibrary.POJO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FullPath {
    /** only absolute path */
    private String pathToRepository;
    /** only absolute path */
    private String author;
    /** only relative path */
    private String repository;
    /** only relative path */
    private String workPathWithRepository;
    /** only relative path */
    private String workPath;
}
