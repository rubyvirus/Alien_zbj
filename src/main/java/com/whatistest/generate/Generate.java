package com.whatistest.generate;

/**
 * Created by rubyvirusqq@gmail.com on 12/06/17.
 * jira:
 * rap:
 * description:
 */
public class Generate {

    public static GenerateHTTP generateHttp;

    public static void main(String[] args) {
        generateHttp = new GenerateHTTP();
        generateHttp.loadAll("baidu", "wd");
    }

}
