package com.week8.RestTemplateDemo.service;

import com.week8.RestTemplateDemo.model.Foo;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class FooService {
    private final RestTemplate restTemplate;
    private final String fooResourceUrl;

    public FooService(RestTemplate restTemplate, @Value("${foo.resource.url}") String fooResourceUrl) {
        this.restTemplate = restTemplate;
        this.fooResourceUrl = fooResourceUrl;
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout)
                .build();
        CloseableHttpClient client = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(config)
                .build();
        return new HttpComponentsClientHttpRequestFactory();
    }


    public Foo getFoo(Long id){
        ResponseEntity<Foo> response = restTemplate.getForEntity(fooResourceUrl + "/" + id, Foo.class);
        return response.getBody();
    }

    public Foo createFoo(Foo foo){
        ResponseEntity<Foo> response = restTemplate.postForEntity(fooResourceUrl, foo, Foo.class);
        return response.getBody();
    }

    public URI createFooUsingPostForLocation(Foo foo) {
        HttpEntity<Foo> request = new HttpEntity<>(foo);
        return restTemplate.postForLocation(fooResourceUrl, request);
    }

    public Foo createFooUsingPostForObject(Foo foo) {
        HttpEntity<Foo> request = new HttpEntity<>(foo);
        return restTemplate.postForObject(fooResourceUrl, request, Foo.class);
    }

    public Foo createFooUsingExchange(Foo foo) {
        HttpEntity<Foo> request = new HttpEntity<>(foo);
        ResponseEntity<Foo> response = restTemplate.exchange(fooResourceUrl, HttpMethod.POST, request, Foo.class);
        return response.getBody();
    }

    public Foo updateFoo(Long id, Foo foo) {
        HttpEntity<Foo> requestEntity = new HttpEntity<>(foo);
        ResponseEntity<Foo> response = restTemplate.exchange(fooResourceUrl + "/" + id, HttpMethod.PUT, requestEntity, Foo.class);
        return response.getBody();
    }

    public boolean deleteFoo(Long id) {
        try {
            restTemplate.delete(fooResourceUrl + "/" + id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public HttpHeaders getFooHeaders() {
        return restTemplate.headForHeaders(fooResourceUrl);
    }

    public Set<HttpMethod> getAllowedMethods() {
        return restTemplate.optionsForAllow(fooResourceUrl);
    }

    public List<Foo> getFoosList() {
        ResponseEntity<Foo[]> responseEntity = restTemplate.getForEntity(fooResourceUrl, Foo[].class);
        return Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
    }

    public Foo[] getFoosArray() {
        ResponseEntity<Foo[]> responseEntity = restTemplate.getForEntity(fooResourceUrl + "/array", Foo[].class);
        return responseEntity.getBody();
    }

    public List<String> getFooNamesFromFooList() {
        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(
                fooResourceUrl + "/names",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {}
        );
        return responseEntity.getBody();
    }
}
