package com.whatistest.util.common;

import com.whatistest.testng.dataprovider.DataProviderFactory;
import com.whatistest.testng.annotation.ZBJTestDataProvider;
import org.testng.annotations.DataProvider;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by rubyvirusqq@gmail.com on 16/05/17.
 * <p>
 * 使用dataProvider方式添加测试数据
 */
public class DataProviderUtil extends DataProviderFactory implements Serializable {


    /**
     * 加载yml数据
     *
     * @param m
     * @return
     */
    @DataProvider(name = "is_yml")
    public Object[][] getDataFromYML(Method m) {
        Map<String, String> param = getParameters(m);
        // 如果当前存在这个文件类型
        if (param.containsKey(ZBJTestDataProvider.params.DATAFILE.name())) {

        }
        Object[][] o1 = new Object[][]{{1, 2}, {3, 4}};
        return o1;
    }

    @DataProvider(name = "is_ymlx")
    public Object[][] getStr() {
        Object[][] o1 = new Object[][]{{1, 2}, {3, 4}};
        return o1;
    }

    @DataProvider(name = "is_excel")
    public Object[][] getExcel() {
        Object[][] o1 = new Object[][]{{1, 2}, {3, 4}};
        return o1;
    }


}
