package com.week8.FeignClientDemo.client;

import com.week8.FeignClientDemo.config.ClientConfiguration;
import com.week8.FeignClientDemo.hystrix.JSONPlaceHolderFallback;
import com.week8.FeignClientDemo.model.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "jplaceholder",
        url = "https://jsonplaceholder.typicode.com/",
        configuration = ClientConfiguration.class,
        fallback = JSONPlaceHolderFallback.class)
public interface PostClient {
    @RequestMapping(method = RequestMethod.GET, value = "/posts")
    List<Post> getPosts();


    @RequestMapping(method = RequestMethod.GET, value = "/posts/{postId}", produces = "application/json")
    Post getPostById(@PathVariable("postId") Integer postId);
}