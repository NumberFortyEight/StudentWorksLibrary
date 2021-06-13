package com.example.studentworkslibrary.POJO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RepositoryInfo {
    private String author;
    private String repositoryName;
    private Node node;
}
