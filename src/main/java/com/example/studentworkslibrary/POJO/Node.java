package com.example.studentworkslibrary.POJO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.jgit.revwalk.RevCommit;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Node {
    private String name;
    private RevCommit revCommit;
    private List<Node> childNodes = new ArrayList<>();
}
