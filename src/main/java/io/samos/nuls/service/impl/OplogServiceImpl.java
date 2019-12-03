package io.samos.nuls.service.impl;

import io.samos.nuls.entity.Oplog;
import io.samos.nuls.repos.OplogRepository;
import io.samos.nuls.repos.UserRepository;
import io.samos.nuls.service.OplogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class OplogServiceImpl implements OplogService {
    @Autowired
    private OplogRepository oplogRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean addOplog(int userId, String opcode, String info) {
        boolean flag = userRepository.existsById(userId);
        if(flag){
            Oplog log = new Oplog();
            log.setUser_id(userId);
            if(opcode != null){
                log.setOp_code(opcode);
                log.setInfo(info);
                log.setCreateTime(new Date());
                log.setUpdateTime(new Date());
                oplogRepository.save(log);
                return true;
            }
        }
        return false;
    }
}
