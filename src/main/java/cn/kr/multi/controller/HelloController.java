package cn.kr.multi.controller;


import cn.kr.multi.config.ConstId;
import cn.kr.multi.dao.TenantInfoDao;
import cn.kr.multi.entity.Student;
import cn.kr.multi.entity.TenantInfo;
import cn.kr.multi.entity.User;
import cn.kr.multi.entity.User2;
import cn.kr.multi.service.HelloService;
import cn.kr.multi.service.StudentService;
import cn.kr.multi.tenant.TenantDataSourceProvider;
import cn.kr.multi.util.Result;
import cn.kr.multi.util.ResultGenerator;
import com.fasterxml.classmate.AnnotationConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.internal.MetadataImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.cfg.Configuration;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.hibernate.query.NativeQuery;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.schema.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpSession;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/")
public class HelloController {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TenantInfoDao tenantInfoDao;

    @RequestMapping
    public Result hello() {
        List<TenantInfo> tenantInfoList = tenantInfoDao.findAll();
        for (TenantInfo info : tenantInfoList) {
            TenantDataSourceProvider.addDataSource(info);
        }
        return ResultGenerator.genSuccessResult(tenantInfoList);
    }

    @RequestMapping("login")
    public Result login(@Param("t") String t) {
        ConstId.Id = t;
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("select")
    public Result getStudent(@Param("t") String t) {
        return ResultGenerator.genSuccessResult(studentService.findAll());
    }

    @Transactional
    @GetMapping("create")
    public Result createDataBase() {
        Session session = entityManager.unwrap(Session.class);
        NativeQuery nativeQuery = session.createNativeQuery("create database class_1");
        nativeQuery.executeUpdate();
        return ResultGenerator.genSuccessResult();
    }



    /**
     * hibernate 动态的 创建表 和 跟新表结构。
     * @return
     */

    @Autowired
    private HelloService helloService;
    @Transactional
    @GetMapping("createTable")
    public Result createTable() {

        helloService.createTable();

//        schemaExport.create(EnumSet.of(TargetType.DATABASE),metadata);
//        schemaUpdate.execute(EnumSet.of(TargetType.DATABASE),metadata,serviceRegistry);


        return ResultGenerator.genSuccessResult();

    }

//    @Transactional
//    @GetMapping("createTable")
//    public Result createTable2() throws IOException, PropertyVetoException {
//
//
//        session = en.createEntityManager().unwrap(Session.class);
//
//
//        StandardServiceRegistry serviceRegistry = session.getSessionFactory().getSessionFactoryOptions().getServiceRegistry();
//
//        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
//        metadataSources.addAnnotatedClass(User.class);
//        Metadata metadata = metadataSources.buildMetadata();
//        SchemaUpdate schemaUpdate = new SchemaUpdate();
//        schemaUpdate.execute(EnumSet.of(TargetType.DATABASE),metadata,serviceRegistry);
//        return ResultGenerator.genSuccessResult();
//
//    }
}
