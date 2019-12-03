package io.samos.nuls.service.impl;

import io.samos.nuls.common.ErrorCode;
import io.samos.nuls.common.RestResult;
import io.samos.nuls.entity.Node;
import io.samos.nuls.entity.User;
import io.samos.nuls.repos.NodeRepository;
import io.samos.nuls.repos.UserRepository;
import io.samos.nuls.service.UserService;
import io.samos.utils.SafeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NodeRepository nodeRepository;
    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public RestResult changePassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findByUsernameAndPassword(username,SafeUtil.getPasswd(oldPassword));
        if(user != null){
           user.setPassword(SafeUtil.getPasswd(newPassword));
           userRepository.save(user);
           return RestResult.resultOf(ErrorCode.ERROR_CODE_SUCCESS,"change password success");
        }
        return RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED,"change password failed");
    }

    @Override
    public RestResult createUsers() {
        Iterable<Node> nodeList = nodeRepository.findAll();
        if(nodeList !=null ){
            for(Node n:nodeList){
                //节点未被删除
                if(n.getCheckstatus()!=3){
                log.info("add "+n.getId()+"'s user");
                createUserByagentAddress(n.getAgentAddress(),n.getId());
                }
            }
            return RestResult.resultOf(ErrorCode.ERROR_CODE_SUCCESS,"add all default user success");
        }
       return RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED,"add all default users failed");
    }
    private void createUserByagentAddress(String agentAddress,int nodeId){

        User user = userRepository.findByagentAddress(agentAddress);
        if(user == null){
            User u = new User();
            u.setAgentAddress(agentAddress);
            u.setNodeId(nodeId);
            u.setRole("user");
            u.setPassword(SafeUtil.getPasswd("nuls123456"));
            u.setUpdateTime(new Date());
            u.setCreateTime(new Date());
            userRepository.save(u);
        }
    }

    @Override
    public RestResult changeUsername(int userId,String username) {
        int count  = userRepository.countUserByUsername(username);
        if(count > 0){
            return RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED,"username already exist,please try again");
        }
        User user = userRepository.findById(userId);
        user.setUsername(username);
        userRepository.save(user);
       return RestResult.resultOf(ErrorCode.ERROR_CODE_SUCCESS,"update username success");
    }

    @Override
    public RestResult login(String usernameOragentAddress, String password, HttpSession session) {
        String encodepwd = SafeUtil.getPasswd(password);
        User user;
        log.info(usernameOragentAddress+" try to login");
        int addressLength = "Nse2gPPEWHuwv5mMLJvV2dvKUMjsmGMk".length();
        if (usernameOragentAddress.length() == addressLength){
            log.info("use default way to login");
             user = userRepository.findByagentAddress(usernameOragentAddress);
        }else{
            user = userRepository.findByUsername(usernameOragentAddress);
        }
        if(user != null) {
            if (user.getPassword().equals(encodepwd)) {
                user.setPassword(null);
                session.setAttribute(session.getId(),user);
                return RestResult.resultOf(ErrorCode.ERROR_CODE_SUCCESS,"login success",user);
            }
        }
        return RestResult.resultOf(ErrorCode.ERROR_CODE_LOGIN_FAILED,"login failed");

    }

    @Override
    public RestResult logout(HttpSession session) {
        session.removeAttribute(session.getId());
        return RestResult.resultOf(ErrorCode.ERROR_CODE_SUCCESS,"logout success");
    }
}
