package io.samos.nuls.controller;

import io.samos.nuls.common.RestResult;
import io.samos.nuls.entity.User;
import io.samos.nuls.service.NodeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller    // This means that this class is a Controller
@RequestMapping
public class ApiController {

    @Autowired
    private NodeService nodeService;

    private static final Logger log = LogManager.getLogger(ApiController.class);

    //根据节点id获取节点信息
    @RequestMapping(path = "/node/{nodeId}")
    @ResponseBody
    public RestResult getNodeById(@PathVariable("nodeId") int id){
        return nodeService.getNodeById(id);
    }
    //根据节点类型获取节点信息
    //type值说明：0 全部节点 1 普通节点 2 开发者节点 3 大使节点
    @RequestMapping(path = "/nodes/{type}")
    @ResponseBody
    public RestResult getNodesByType(@PathVariable("type")Integer type, HttpSession session){
        if(type == null){
            type = 0;
        }
        User u = (User) session.getAttribute(session.getId());
        if(u == null) {
            log.info("everybody can see these nodes");
            return nodeService.getNodesByType(type,"user");
        } else {
            log.info("If you aren't admin ,you only see your node .If you are admin you will see all nodes.");
            return nodeService.getNodesByType(type,u.getRole());
        }

    }

    @RequestMapping(path = "/search")
    @ResponseBody
    public RestResult findNodesByAgentIdOrAgentAddress(@RequestParam String input){
        return nodeService.findNodesAgentIdOrAgentAddress(input);
    }

}
