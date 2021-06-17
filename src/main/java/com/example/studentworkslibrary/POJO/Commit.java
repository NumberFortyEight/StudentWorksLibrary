package com.example.studentworkslibrary.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Commit {
    private String message;
    private String author;
    private String simpleDateFormat;
}