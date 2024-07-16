package com.example.auth.data.dao;

import com.example.auth.data.dataobject.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleRepository  extends JpaRepository<SysRole,Long> {
}
