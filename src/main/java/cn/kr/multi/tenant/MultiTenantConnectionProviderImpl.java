package cn.kr.multi.tenant;

import cn.kr.multi.config.ConstId;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 这个类是Hibernate框架拦截sql语句并在执行sql语句之前更换数据源提供的类
 * @author lanyuanxiaoyao
 * @version 1.0
 */
@Component
public class MultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {


    // 在没有提供tenantId的情况下返回默认数据源
    @Override
    protected DataSource selectAnyDataSource() {
//        if (!"".equals(ConstId.Id)) {
//            return TenantDataSourceProvider.getTenantDataSource(ConstId.Id);
//        }
        return TenantDataSourceProvider.getTenantDataSource("Default");
    }

    // 提供了tenantId的话就根据ID来返回数据源
    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        DataSource tenantDataSource = TenantDataSourceProvider.getTenantDataSource(tenantIdentifier);
        return tenantDataSource;

    }

    @Override
    public Connection getAnyConnection() throws SQLException {

        return selectDataSource("class_1").getConnection();
    }
}
