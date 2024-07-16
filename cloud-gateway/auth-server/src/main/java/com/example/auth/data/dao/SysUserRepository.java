package com.example.auth.data.dao;

import com.example.auth.data.dataobject.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser,Long> {

    SysUser findByUsernameAndStatus(String username,Integer status);

}
