package com.jpsoft.cms.admin.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpsoft.cms.admin.entity.LanmuType;

import com.jpsoft.cms.sysmgr.entity.SysMenu;
import com.jpsoft.cms.sysmgr.entity.SysUser;

@Repository(value="lanmuTypeDao")
public interface LanmuTypeDao extends PagingAndSortingRepository<LanmuType, String>,JpaSpecificationExecutor<LanmuType>{
	
	

	@Modifying
	@Query("delete from LanmuType n where n.id in(:ids)")
	void deleteByIds(@Param("ids") List<String> ids);
	
	
	@Query("select n from LanmuType n where n.id in (:ids)")
	public List<LanmuType> findByIds();
	
	/**
	 * pageSearch(@Param("name")String name)这里的@Param一定不能少，否则会报错
	 * @param name
	 * @param pageable
	 * @return
	 */
	@Query(" select new map(n.id as id,n.name as name,n.shuju as shuju)" +
			   " from LanmuType n where n.name like:name")
	Page<Map> pageSearch(@Param("name")String name, Pageable pageable);
	
	


}
