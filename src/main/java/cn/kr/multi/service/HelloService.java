package cn.kr.multi.service;

import cn.kr.multi.entity.User;
import cn.kr.multi.entity.User2;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.schema.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.EnumSet;

@Service
public class HelloService {


    @Autowired
    private EntityManager entityManager;

    public void createTable() {
        Session session = entityManager.unwrap(Session.class);

        StandardServiceRegistry serviceRegistry = session.getSessionFactory().getSessionFactoryOptions()
                .getServiceRegistry();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);


        metadataSources.addAnnotatedClass(User.class);

        metadataSources.addAnnotatedClass(User2.class);

        Metadata metadata = metadataSources.buildMetadata();
        SchemaUpdate schemaUpdate = new SchemaUpdate();
        SchemaExport schemaExport = new SchemaExport();
        schemaExport.execute(EnumSet.of(TargetType.DATABASE), SchemaExport.Action.CREATE,metadata);
        session.close();
    }
}
