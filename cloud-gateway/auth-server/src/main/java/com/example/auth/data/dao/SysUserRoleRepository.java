package com.example.auth.data.dao;

import com.example.auth.data.dataobject.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRoleRepository extends JpaRepository<SysUserRole,Long> {

    List<SysUserRole> findByUserId(Long userId);

}
