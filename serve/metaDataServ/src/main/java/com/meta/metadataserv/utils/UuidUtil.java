package com.meta.metadataserv.utils;

import java.util.UUID;

public class UuidUtil {
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getUuid(Object seed) throws Exception {
        return UUID.nameUUIDFromBytes(TransferUtil.objectToByte(seed)).toString().replaceAll("-", "");
    }
}
