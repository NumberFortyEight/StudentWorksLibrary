package com.example.studentworkslibrary.POJO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Node {
    private String name;
    private int commitTime;
    private List<Node> childNodes = new ArrayList<>();
}
