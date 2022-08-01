package com.meta.metadataserv.auth.ctrl;

import com.meta.metadataserv.auth.feign.AuthFeign;
import com.meta.metadataserv.domain.result.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.service.TokenEndpoint;

import javax.annotation.Resource;
import java.util.Map;

@Api(tags = "登录认证")
@RestController
@RequestMapping("/metaData/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {
    @Resource
    ConsumerTokenServices tokenServices;

    @Resource
    private AuthFeign authFeign;

    @ApiOperation("登录获取token")
    @PostMapping("/token")
    public RespResult userAuth(@RequestParam Map<String, String> parameters){
        try {
            OAuth2AccessToken token = authFeign.userAuth(parameters);
            return RespResult.ok(token);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return RespResult.error(e.getMessage(), e.getMessage());
        }
    }

    @ApiOperation("注销")
    @PostMapping("/logout")
    public RespResult logout(@RequestParam String token){
        if (tokenServices.revokeToken(token)) {
            return RespResult.ok();
        }
        return RespResult.error("注销失败", null);
    }
}
