package com.week8.FeignClientDemo.controller;

import com.week8.FeignClientDemo.client.PostClient;
import com.week8.FeignClientDemo.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Import(FeignClientsConfiguration.class)
public class PostFeignController {
    private final PostClient postClient;

    @GetMapping(value = "posts")
    public List<Post> getPosts(){
        return postClient.getPosts();
    }

    @GetMapping(value = "posts/{id}")
    public Post getPostById(@PathVariable(value = "id") Integer id){
        return postClient.getPostById(id);
    }
}
