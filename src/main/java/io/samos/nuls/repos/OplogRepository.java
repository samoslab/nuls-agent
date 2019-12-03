package io.samos.nuls.repos;

import io.samos.nuls.entity.Oplog;
import io.samos.nuls.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface OplogRepository extends CrudRepository<Oplog, Integer> {

}
