package com.springrest.restserver.common;

import org.hibernate.dialect.MySQL5Dialect;

public class MysqlNdbDialect extends MySQL5Dialect {
    public String getTableTypeString() {
        return " engine=ndbcluster";
    }
}
