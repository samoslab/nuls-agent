package io.samos.nuls.service;

public interface OplogService {
    boolean addOplog(int userId,String opcode,String info);
}
