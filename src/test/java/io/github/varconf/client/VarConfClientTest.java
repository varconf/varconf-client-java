package io.github.varconf.client;

import com.alibaba.fastjson.JSON;
import io.github.varconf.client.vo.PullAppResult;
import io.github.varconf.client.vo.PullKeyResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config.xml")
public class VarConfClientTest {

    @Autowired
    private TestBean testBean;

    @Autowired
    private VarConfClient varConfClient;

    @Test
    public void beanConfig() throws IOException {
        System.out.println(JSON.toJSONString(testBean));
    }

    @Test
    public void queryAppConfig() throws IOException {
        PullAppResult pullAppResult = varConfClient.queryAppConfig(false, 0);
        System.out.println(JSON.toJSONString(pullAppResult));
    }

    @Test
    public void queryKeyConfig() throws IOException {
        PullKeyResult pullKeyResult = varConfClient.queryKeyConfig("key", false, 0);
        System.out.println(JSON.toJSONString(pullKeyResult));
    }
}