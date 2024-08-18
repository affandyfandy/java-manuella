package com.week8.FeignClientDemo.hystrix;

import com.week8.FeignClientDemo.client.PostClient;
import com.week8.FeignClientDemo.model.Post;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class JSONPlaceHolderFallback implements PostClient {
    @Override
    public List<Post> getPosts() {
        return Collections.emptyList();
    }

    @Override
    public Post getPostById(Integer postId) {
        return null;
    }
}