package com.springrest.restserver.business.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springrest.restserver.business.entity.user.Session;


public interface SessionRepository extends JpaRepository<Session,String> {
	
	@Modifying  
	@Query(" Update Session SET status =:status WHERE userId=:userId And status="+Session.STATUS_LOGIN)
	public void updateSessionStatusByUserId( @Param("status") Integer status ,@Param("userId") Long userId );
}

