package com.dantefung.springbootmvc.sign;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@ConditionalOnProperty(value = "configuration.swith.sign.enabled", havingValue = "true", matchIfMissing = false)
@Slf4j
public class SignConfig {

    @Value("${boot.sign.privateKey:null}")
    private String privateKey;

    private String privateKeyString;
    @Autowired
    private RestTemplate restTemplate;

    private Map<String, String> map = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("初始化私钥信息");
        ClassPathResource resource = new ClassPathResource(privateKey);
        try {
            this.privateKeyString = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("", e);
        }
    }


    public String getPrivateKeyString() {
        return privateKeyString;
    }

    public void setPrivateKeyString(String privateKeyString) {
        this.privateKeyString = privateKeyString;
    }

    public String getPublicKey(String appId) {
        if (map.containsKey(appId)) {
            return map.get(appId);
        } else {
            // 从应用中心中获取服务，restTemplate被添加了@Loadbalance
        	/*String url = String.format("http://%s/api/appsys/getServerKeyById?appSysId=%s",
                    "os-kernel-appsysctr-microsvc".toUpperCase(), appId);
            ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
            if (result.getStatusCode().is2xxSuccessful()) {
                String body = result.getBody();
                if (StringUtils.isNotBlank(body) && !body.equals("null")) {
                    JSONObject jsonObject = JSONObject.parseObject(body);
                    String publicKey = jsonObject.getString("publicKey");// 获取公钥
                    map.put(appId, publicKey);
                    return publicKey;
                }
            }
             return null;
            */
            // 本地模拟获取接入方的公钥
			String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg6TIhNb0vBBl54/GjDgHe0E/uwVmTCPSKPCAUHtxe8e75XP1aE9KbjD3/Ph4uRfjC1kwcgxowxov8yU0A5rH3a5XKwUm0zpMr4OIzTrok9ebxV2F5JYJvZ5Sx5tzQQMT97Z1o3TLEgowAIDMuJiIwVXGn4W8VZl11eeA9Ry2nQtM04cj7tHlKpHap5Uvl3W7JDg6e4jS1Te9ClrHtF59sHTv6Y1f7bKAMntha2k5UdFrbGvwSKP3/s/4D4pCa9KuHqIaFRoDwNqQDCENFoNGkDAZJMVzsG6ceGcC4nFR0zU1lFiiE0JcOUgjg25lu2btcfgk/IoUslHPlAeEs2+POwIDAQAB";
			map.put(appId, publicKey);
			return publicKey;



        }
    }
}
