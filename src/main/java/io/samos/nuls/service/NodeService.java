package io.samos.nuls.service;

import io.samos.nuls.common.RestResult;

public interface NodeService {

     RestResult getNodeById(int nodeId);
     RestResult getNodesByType(int type,String role);
     RestResult getNodesByUser(int userId);
     RestResult updateNodesFromApi();
    //code值说明：0 已审核 1 待审核 2 审核不通过 3 已删除
     //1
     RestResult updateNodeInfo(int nodeId, String link, String description, String email, String phone, String avatar);
    // 0
     RestResult nodePass(int nodeId);
     //2
     RestResult nodeReject(int nodeId);
     //3
     RestResult deleteNode(int nodeId);

     RestResult findNodesAgentIdOrAgentAddress(String input);
}
