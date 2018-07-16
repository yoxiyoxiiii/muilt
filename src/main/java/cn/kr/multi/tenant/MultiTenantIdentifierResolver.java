package cn.kr.multi.tenant;

import cn.kr.multi.config.ConstId;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.context.spi.CurrentSessionContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

/**
 * 这个类是由Hibernate提供的用于识别tenantId的类，当每次执行sql语句被拦截就会调用这个类中的方法来获取tenantId
 * @author lanyuanxiaoyao
 * @version 1.0
 */
@Slf4j
@Configuration
public class MultiTenantIdentifierResolver implements CurrentTenantIdentifierResolver{

    // 获取tenantId的逻辑在这个方法里面写
    @Override
    public String resolveCurrentTenantIdentifier() {
        if (!"".equals(ConstId.Id)){
            log.error("ConstId.Id :{}", ConstId.Id);
            return ConstId.Id;
        }
        return "Default";
    }

    @Autowired
    private EntityManager entityManager;

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
