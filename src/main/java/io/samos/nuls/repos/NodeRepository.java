package io.samos.nuls.repos;

import io.samos.nuls.entity.Node;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NodeRepository extends CrudRepository<Node, Integer> {


    @Query("select  n from Node n where n.agentId = :agentId ")
    Node findByAgentId(@Param("agentId") String agentId);

    @Query("select n from Node n where n.agentId like %:input% or n.agentAlias like %:input%")
    List<Node> findNodesByAgentIdOrAgentAlias(@Param("input")String input);

    @Query("select n from Node n where n.id = :id")
    Node findById(@Param("id")int id);

    @Query("select n from Node n where n.type = :type ")
    List<Node> findNodesByType(@Param("type")int type);

    @Modifying
    @Transactional
    @Query("update Node n set n.checkstatus = :code where n.id = :id")
    int updateStatusById(@Param("code")int code,@Param("id") int id);

}
