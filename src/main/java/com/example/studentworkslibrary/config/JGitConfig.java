package com.example.studentworkslibrary.config;

import com.example.studentworkslibrary.POJO.FullPath;
import com.example.studentworkslibrary.services.JGit.JGitContent;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class JGitConfig {

    @Bean
    @Scope("prototype")
    public JGitContent jGitContent(FullPath fullPath, Git git, RevCommit revCommit){
        return new JGitContent(fullPath, git, revCommit);
    }

}
