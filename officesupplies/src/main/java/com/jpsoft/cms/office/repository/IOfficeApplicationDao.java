package com.jpsoft.cms.office.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpsoft.cms.office.entity.OfficeApplication;


@Repository("officeApplicationDao")
public interface IOfficeApplicationDao extends PagingAndSortingRepository<OfficeApplication,String>,JpaSpecificationExecutor<OfficeApplication> {
	
	/*
	@Query("select from OfficeApplication where id in (:ids)")
	public List<OfficeApplication> findByIds(@Param("ids") List<String> ids);
	*/
}
