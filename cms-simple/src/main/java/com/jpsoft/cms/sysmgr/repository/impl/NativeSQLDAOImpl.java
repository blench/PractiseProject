package com.jpsoft.cms.sysmgr.repository.impl;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

import com.jpsoft.cms.sysmgr.repository.NativeSQLDAO;

@Repository(value="nativeSQLDAO")
public class NativeSQLDAOImpl implements NativeSQLDAO {
	@Resource(name="entityManagerFactory")
	private EntityManagerFactory emf;
	
	@Override
	public int executeSql(String sql) {
		// TODO Auto-generated method stub
		int affectCount = 0;
		
		EntityManager em = emf.createEntityManager();

		Query query = em.createNativeQuery(sql);
		EntityTransaction trans = em.getTransaction();
		
		try{	
			trans.begin();
			affectCount = query.executeUpdate();
			
			trans.commit();
		}
		catch(Exception ex){
			ex.printStackTrace();
			trans.rollback();
		}
		finally{
			em.close();
		}
		
		return affectCount;
	}
}