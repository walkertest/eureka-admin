package cn.springcloud.eureka;

import cn.springcloud.eureka.http.HttpUtil;
import cn.springcloud.eureka.model.EurekaApplicationModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netflix.discovery.shared.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

@Slf4j
public class GetAllApplications {

    @Test
    public void GetAllApplications() {

        String url = "https://eureka-udb-test.huya.info";
        String urlpath = "/eureka/apps";
        String resultUlr = url + urlpath;
        String result = HttpUtil.get(resultUlr);
        log.info("result: {}", result);

        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject applications = (JSONObject) jsonObject.get("applications");
        String applicationStr = applications.getString("application");;
        List<EurekaApplicationModel> apps = JSON.parseObject(applicationStr, new com.alibaba.fastjson.TypeReference<List<EurekaApplicationModel>>(){});
        log.info("apps: {}", apps);
    }
}
