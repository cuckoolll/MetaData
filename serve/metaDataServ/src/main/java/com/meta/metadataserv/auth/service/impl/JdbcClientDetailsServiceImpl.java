package com.meta.metadataserv.auth.service.impl;

import lombok.SneakyThrows;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

public class JdbcClientDetailsServiceImpl extends JdbcClientDetailsService {
    public static final String FIND_CLIENT_DETAILS_SQL = "select client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove from oauth_client_details order by client_id";

    public static final String SELECT_CLIENT_DETAILS_SQL = "select client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove from oauth_client_details where client_id = ?";

    public JdbcClientDetailsServiceImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    @SneakyThrows
    public ClientDetails loadClientByClientId(String clientId)  {
        return super.loadClientByClientId(clientId);
    }
}
