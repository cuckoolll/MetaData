package com.meta.metadataserv.enums;

import lombok.Getter;

@Getter
public enum DbType {
    mysql("mysql", "com.mysql.cj.jdbc.Driver", "jdbc:mysql://");


    private String type;
    private String drive;
    private String preUrl;

    DbType(String type, String drive, String preUrl) {
        this.type = type;
        this.drive = drive;
        this.preUrl = preUrl;
    }

    public String getDbUrl(final String url, final String schema) {
        return preUrl + url + "/" + schema;
    }
}
