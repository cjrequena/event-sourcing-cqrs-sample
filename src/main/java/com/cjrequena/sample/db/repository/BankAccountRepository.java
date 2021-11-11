package com.cjrequena.sample.db.repository;

import com.cjrequena.sample.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, UUID>, JpaSpecificationExecutor<BankAccountEntity> {
}
