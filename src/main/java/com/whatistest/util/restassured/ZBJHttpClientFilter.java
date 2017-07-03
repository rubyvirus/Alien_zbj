package com.whatistest.util.restassured;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.http.client.HttpClient;

/**
 * 使用过滤器，返回AbstractHttpClient
 * FilterContext 链 不需要下传
 * Created by rubyvirusqq@gmail.com on 08/05/17.
 */
public class ZBJHttpClientFilter implements Filter {

    private HttpClient httpClientOther;

    public HttpClient getHttpClientOther() {
        return httpClientOther;
    }

    public void setHttpClientOther(HttpClient httpClientOther) {
        this.httpClientOther = httpClientOther;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        this.setHttpClientOther(requestSpec.getHttpClient());
        return null;
    }

}
