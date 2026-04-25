package com.bank.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.account.entity.Account;
import jakarta.persistence.LockModeType;

public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query("SELECT a.accountNumber FROM Account a WHERE a.accountType = :type ORDER BY a.accountId DESC")
	String findLastAccountNumberByType(@Param("type") String type);
	
	List<Account> findByCustomerId(Long customerId);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT a FROM Account a WHERE a.id = :id")
	Account findByIdForUpdate(@Param("id") Long id);
	
	
}
