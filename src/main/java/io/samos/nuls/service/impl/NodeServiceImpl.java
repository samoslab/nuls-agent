package io.samos.nuls.service.impl;

import io.samos.nuls.common.ErrorCode;
import io.samos.nuls.common.NodeListResponse;
import io.samos.nuls.common.RestResult;
import io.samos.nuls.entity.Node;
import io.samos.nuls.entity.User;
import io.samos.nuls.repos.NodeRepository;
import io.samos.nuls.repos.UserRepository;
import io.samos.nuls.service.NodeService;
import io.samos.utils.HttpUtils;
import io.samos.utils.StoreUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static javax.xml.bind.DatatypeConverter.parseBase64Binary;


@Service
public class NodeServiceImpl implements NodeService {

    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private UserRepository userRepository;


    private String listurl="https://api.nuls.io/";
    private static final Logger log = LogManager.getLogger(NodeServiceImpl.class);


    @Override
    public RestResult getNodesByType(int type,String role) {
        //根据type获取节点（不包括已删除节点)
        List<Node> nodeList = new ArrayList<>();
        if(type == 0){
            Iterable<Node> nodes = nodeRepository.findAll();
            for(Node n:nodes){
                nodeList.add(n);
            }
        }else {
           nodeList = nodeRepository.findNodesByType(type);
        }
        //获取全部审核通过的节点
        if(!"admin".equals(role)) {
            for(int i=0;i<nodeList.size();i++) {
                Node n = nodeList.get(i);
                if(n.getCheckstatus() != 0) {
                    nodeList.remove(n);
                }
            }

        }
        String msg;
        switch (type){
            case 0: msg = "get all nodes";break;
            case 1: msg = "get all common nodes";break;
            case 2: msg = "get all developer's nodes";break;
            case 3: msg = "get all ambassador's nodes";break;
            default:msg = "";

        }
       if(nodeList !=null && nodeList.size()>0){
           return RestResult.resultOf(ErrorCode.ERROR_CODE_SUCCESS, msg,nodeList);
       }
        return RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED,"can't get nodes or none node");
    }

    @Override
    public RestResult getNodeById(int id) {
        Node node = nodeRepository.findById(id);
        if(node != null) {
            return RestResult.resultOf(ErrorCode.ERROR_CODE_SUCCESS, "get a node", node);
        }else {
            return RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED, "can't get this node ");
        }
    }

    @Override
    public RestResult getNodesByUser(int userId) {
        User user = userRepository.findById(userId);
        if(user == null){
            log.error("this user is not exist");
           return RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED,"the user is not exist");
        }
        Iterable<Node>  nodes = nodeRepository.findAll();
        List<Node> nodeList = new ArrayList<>();
        //管理员获取全部节点（包括已删除节点）
        if (user.getRole().equals("admin")) {
            for(Node n:nodes){
                nodeList.add(n);
            }
            //普通用户获取自己节点（包括自己节点中审核未通过的）
        }else{
            for (Node n:nodes){
                if(n.getId()==user.getNodeId()){
                    nodeList.add(n);
                }
            }
        }
        return RestResult.resultOf(ErrorCode.ERROR_CODE_SUCCESS,"get nodes success",nodeList);
    }


    public RestResult updateNodesFromApi() {
            try {
                NodeListResponse nodeListResponse = HttpUtils.getNodesList(listurl);
                if (nodeListResponse.getResult() == null) {
                    return RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED, "can't get nodeList from api");
                }
                NodeListResponse.Result result = nodeListResponse.getResult();
                //获取全部节点
                List<Node> nodeList = result.getList();
                System.out.println(nodeList);
                //更新节点
                if (nodeList != null && nodeList.size() > 0) {
                    for (Node n : nodeList) {
                        Node localNode = nodeRepository.findById(n.getId());
                        if (localNode != null) {
                            n.setLink(localNode.getLink());
                            n.setDescription(localNode.getDescription());
                            n.setPhone(localNode.getPhone());
                            n.setEmail(localNode.getEmail());
                            n.setAvatar(localNode.getAvatar());
                            n.setCheckstatus(localNode.getCheckstatus());
                        }
                        nodeRepository.save(n);
                    }
                    return RestResult.resultOf(ErrorCode.ERROR_CODE_SUCCESS, "update all nodes success");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        return RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED,"update all nodes failed");
    }

    //role 为admin可以更新任意节点 role为user只能更新自己节点
    //user 一个用户只有一个节点
    @Override
    public RestResult updateNodeInfo(int nodeId, String link, String description, String email, String phone, String avatar) {
        try {
                //更新节点并把节点设置为未审核
                //问题：审核阶段无法展示当前节点
                Node node = nodeRepository.findById(nodeId);
                node.setLink(link);
                node.setDescription(description);
                node.setEmail(email);
                node.setPhone(phone);
                node.setCheckstatus(1);

                log.info("".equals(avatar));//false
                if(avatar.contains("http")||avatar.length()<10){
                    node.setAvatar(avatar);
                }else {
                        log.debug(avatar);
                        String data = avatar.substring(avatar.indexOf(','));
                        log.debug(avatar);

                        byte[] imgBytes = parseBase64Binary(data);
                        String urlPath = StoreUtils.upload(imgBytes)+"?imageMogr2/thumbnail/x64/format/webp/blur/1x0/quality/75|imageslim";
                        node.setAvatar(urlPath);
                }
                nodeRepository.save(node);
                return RestResult.resultOf(ErrorCode.ERROR_CODE_SUCCESS,"update node info success");
        }catch (Exception e){
            e.printStackTrace();
        }
        return RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED,"The user hasn't node or Permission denied ");
    }


    // 0 已审核 1 待审核 2 审核不通过 3 已删除
    private RestResult updateCheckStatus(int code, int nodeId) {
        String msg ;
        switch (code){
            case 0 : msg = "checked";
                break;
            case 1 : msg ="waiting for checking";
                break;
            case 2 : msg = "check Failed";
                break;
            case 3 : msg = "delete success";
            break;
            default:msg = "ok";
        }
        try {
            Node node = nodeRepository.findById(nodeId);
            if(node != null){
                nodeRepository.updateStatusById(code,nodeId);
                return RestResult.resultOf(ErrorCode.ERROR_CODE_SUCCESS, msg);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED,msg+"failed");
    }

    @Override
    public RestResult deleteNode(int nodeId) {
        RestResult result = updateCheckStatus(3,nodeId);
        return result;
    }

    @Override
    public RestResult nodePass(int nodeId) {
        RestResult result = updateCheckStatus(0,nodeId);
        return result;
    }

    @Override
    public RestResult nodeReject(int nodeId) {
        RestResult result = updateCheckStatus(2,nodeId);
        return result;
    }

    @Override
    public RestResult findNodesAgentIdOrAgentAddress(String input) {
        List<Node> nodeList = nodeRepository.findNodesByAgentIdOrAgentAlias(input);
        if(nodeList!=null && nodeList.size()>0){
            return RestResult.resultOf(ErrorCode.ERROR_CODE_SUCCESS,"search those nodes",nodeList);
        }
        return RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED,"no result");
    }
}
