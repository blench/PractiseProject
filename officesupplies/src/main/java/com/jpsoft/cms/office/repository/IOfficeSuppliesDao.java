package com.jpsoft.cms.office.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpsoft.cms.office.entity.OfficeSupplies;


@Repository(value="officeSuppliesDao")
public interface IOfficeSuppliesDao extends PagingAndSortingRepository<OfficeSupplies, String>,JpaSpecificationExecutor<OfficeSupplies>{
	
	
	@Modifying
	@Query("delete from OfficeSupplies where id in(:ids)")
	void deleteByIds(@Param("ids")List<String> ids);

	@Modifying
	@Query("delete from OfficeSupplies where id=?1")
	void deleteById(String id);
	
	@Query("select s from OfficeSupplies s where s.name =?1")
	List<OfficeSupplies> isExist(String name);
	
	@Query("select m from OfficeSupplies m where m.id in (:ids)")
	public List<OfficeSupplies> findByIds(@Param("ids") List<String> ids);
}
