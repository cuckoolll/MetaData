package com.meta.metadataserv.auth.feign;

import com.meta.metadataserv.domain.result.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@FeignClient(name = "auth", url = "http://localhost:9080")
public interface AuthFeign {
    /**
     * 登录认证
     * @return
     */
    @PostMapping("/oauth/token")
    OAuth2AccessToken userAuth(@ApiIgnore @RequestParam Map<String, String> parameters);
}
