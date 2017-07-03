## 构建请求几个要素
1. 请求地址
    - BaseURL
    - RequestPath
2. 认证信息
    - Cookie
    - Token
    - DK
3. 请求参数
    - Map

## 读取配置方式
- 默认配置（没有指定特定配置,所有请求使用默认配置）
    1. 请求地址，默认情况;
    2. 认证信息，默认情况，默认用户.
- 特定配置（特定信息默认不指定,制定了特定信息,就不在使用默认配置）
    1. 特定请求地址;
    2. 特定认证信息;
    3. 特定参数.
## TestNG组织用例
1. multi suite
2. class
3. method


## 加载用例全流程
1. 读取全局配置（yaml）,数据项如下：
    1. BaseURL
    2. UserId
2. 读取全局测试数据（yml,excel）,共享数据（所有用例），如：
    1. 用户名
3. 加载单个用例配置（yaml）,数据项如下：
    1. URL(BaseURL+Path)
    2. UserId
    3. RequestPath
4. 加载单个用力数据（yaml,excel），隔离数据（单个用例），如：
    1. 商品价格
5. 为每个用例构造信息
    1. 认证信息
    2. 全路径地址
    3. 参数
6. 验证返回参数

## 用例实现
1. 重写TestNG IAnnotationTransformer，@Test方法之前设置dataProvider
    1. 缺点：不能实现数据共享，每个case重复的字段都要写一遍
    2. 优点：不用手动组装请求参数
    使用例子：@ZBJTestDataProvider(dataFile="")
2. 使用注解加载测试数据到Ehcache中，暂时不实现
    1. 优点：实现数据数据共享
    2. 缺点：每个用例都要手动组装请求数据
    3. @DataFile(Path="", Scope="")
3. 重写TestNG IClassListener方法，执行class用例之前加载配置到ehcache
    1. 测试类执行前，加载测试配置
4. 使用rest-assured方式组装请求

## 用例详细执行流程
> Given 给定一些条件，When 当执行一些操作时，Then 期望得到某个结果
1. 加载测试数据
2. 选择请求方式
3. 配置请求参数信息
4. 添加断言，可以使用默认的或者使用jsonPath

### 测试数据存放格式
1. 用例只需要一条数据，使用yml. 运用yml特性，数据共享
2. 用例需要使用多条数据，使用excel