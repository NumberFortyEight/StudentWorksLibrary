package com.example.studentworkslibrary.POJO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileModel {
    private State state;
    private String name;
    private Integer commitTime;
    private String href;
}