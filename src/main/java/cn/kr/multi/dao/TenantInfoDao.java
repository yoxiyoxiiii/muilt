package cn.kr.multi.dao;

import cn.kr.multi.entity.TenantInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantInfoDao extends JpaRepository<TenantInfo, Integer> {
}
