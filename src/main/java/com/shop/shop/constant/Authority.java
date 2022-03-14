package com.shop.shop.constant;

public class Authority {
    public static final String[] USER_AUTHORITIES = { "user:read"};
    public static final String[] ADMIN_AUTHORITIES = { "user:read", "user:create", "user:delete", "user:update"};
}
