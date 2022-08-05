package com.meta.metadataserv.auth.config;

import com.meta.metadataserv.domain.sys.UserVo;
import com.meta.metadataserv.auth.service.impl.JdbcClientDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter{
    private AuthenticationManager authenticationManager;
    private DataSource dataSource;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(tokenEnhancer()); //配置JWT的内容增强器
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
        endpoints.authenticationManager(authenticationManager)
                .tokenEnhancer(tokenEnhancerChain)
                .reuseRefreshTokens(true);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        JdbcClientDetailsServiceImpl jdbcClientDetailsService = new JdbcClientDetailsServiceImpl(dataSource);
        jdbcClientDetailsService.setFindClientDetailsSql(JdbcClientDetailsServiceImpl.FIND_CLIENT_DETAILS_SQL);
        jdbcClientDetailsService.setSelectClientDetailsSql(JdbcClientDetailsServiceImpl.SELECT_CLIENT_DETAILS_SQL);
        clients.withClientDetails(jdbcClientDetailsService);
    }

    /**
     * JWT内容增强
     * 如果你想往JWT中添加自定义信息的话，比如说登录用户的ID，可以自己实现TokenEnhancer接口；
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> map = new HashMap<>(2);
            UserVo user = (UserVo) authentication.getUserAuthentication().getPrincipal();
            map.put("userId", user.getId());
            map.put("userName", user.getUsername());
            map.put("nickName", user.getNickName());
            map.put("email", user.getEmail());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
            return accessToken;
        };
    }
}