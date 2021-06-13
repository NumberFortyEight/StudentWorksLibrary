package com.example.studentworkslibrary.POJO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Content<T> {
    private final String name;
    private final T content;
}
