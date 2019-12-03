package io.samos.nuls.repos;

import io.samos.nuls.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("select u from User u where u.id = :id")
    User findById(@Param("id")int id);

    @Query("select u from User u where u.agentAddress = :agentAddress")
    User findByagentAddress(@Param("agentAddress")String agentAddress);

    @Query("select u from User u where u.username = :username ")
    User findByUsername(@Param("username") String username);

    @Query("select count(u) from User u where u.username=:username")
    int countUserByUsername(@Param("username")String username);

    @Query("select u from User u where u.username = :username and u.password = :password")
    User findByUsernameAndPassword(@Param("username")String username,@Param("password")String password);

}
