package com.jpsoft.cms.admin.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpsoft.cms.admin.entity.Lanmu;
import com.jpsoft.cms.admin.entity.News;
import com.jpsoft.cms.admin.entity.LanmuType;
import com.jpsoft.cms.sysmgr.entity.SysMenu;
import com.jpsoft.cms.sysmgr.entity.SysUserRole;

@Repository(value="lanmuDAO")
public interface LanMuDao extends PagingAndSortingRepository<Lanmu, String>,JpaSpecificationExecutor<Lanmu> {
	
	@Query(" select new map(n.id as id ,n.bianma as bianma ,n.sortNo as sortNo ,n.createTime as createTime,n.creator as creator," +
			"n.cover as cover ,n.parent.id as parentId,n.currentLanmu as currentLanmu,n.lanmuTypeId as lanmuTypeId,n.name as name)" +
			   " from Lanmu n where n.name like :name")
		Page<Map> pageSearch(@Param("name")String name,Pageable pr);
		
		@Modifying
		@Query("delete from Lanmu where id in(:ids)")
		void deleteByIds(@Param("ids") List<String> ids);
		
		@Query("select m from LanmuType rm,Lanmu m where rm.id = m.id and rm.id = :id")
		List<LanmuType> findTypeByLanmuId(@Param("id") String id);
		
		@Query("select count(*) from Lanmu m where m.parent.id =:parentId ")
		public long getChildrenCount(@Param("parentId")String parentId);
		
		@Query("select m from Lanmu m  where m.currentLanmu = :id")
		List<Lanmu> findLanmuByCurrentId(@Param("id")String id);
			
		@Query("select m from Lanmu m where m.id in (select rm.id from Lanmu rm where rm.parent.id = :parentId)")
		List<Lanmu> findByParentId(@Param("parentId")String parentId);

		
		@Query("select count(*) from Lanmu  where parent.id = :parentId and displayType  = :displayType")
		public long getChildrenCount(@Param("parentId")String parentId, @Param("displayType")String displayType);

		
//		@Query("select m from Lanmu m where m.parent.id = m.id")
//		List<Lanmu> getChildrenByParentId(@Param("id")String id);
//		
		
}
