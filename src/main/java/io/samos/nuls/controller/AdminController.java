package io.samos.nuls.controller;


import io.samos.nuls.common.ErrorCode;
import io.samos.nuls.common.RestResult;
import io.samos.nuls.entity.User;
import io.samos.nuls.service.NodeService;
import io.samos.nuls.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller    // This means that this class is a Controller
@RequestMapping(path = "/admin")
public class AdminController {

    private static final Logger log = LogManager.getLogger(AdminController.class);


    @Autowired
    private NodeService nodeService;
    @Autowired
    private UserService userService;

    //账户部分
    //创建默认用户
    @RequestMapping(path="/createUsers")
    @ResponseBody
    public RestResult createUsers (HttpSession session) {
       User u =  (User)session.getAttribute(session.getId());

        if(u == null){
            return RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED,"please login");
        }else {
            if(!"admin".equals(u.getRole())){
                return RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED,"not admin");
            }
        }
       RestResult result = userService.createUsers();
       return result;
    }
    //修改用户名
    @RequestMapping(path = "/changeUsername")
    @ResponseBody
    public RestResult changeUsername(@RequestParam String username,HttpSession session){
        User u = (User)session.getAttribute(session.getId());
        if(u==null){
            return RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED,"please login");
        }
        RestResult result = userService.changeUsername(u.getId(),username);
        return result;
    }
    //修改用户密码
    @PostMapping(path = "/changePassword")
    @ResponseBody
    public RestResult changePassword(@RequestParam String oldPassword,
                                        @RequestParam String newPassword,
                                     HttpSession session){

        User u =  (User)session.getAttribute(session.getId());
        if(u==null){
            return RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED,"please login ");
        }
        RestResult result = userService.changePassword(u.getUsername(),oldPassword,newPassword);
        return result;
    }

    //节点部分

    //更新节点展示信息
    //role 为admin可以更新任意节点 role为user只能更新自己节点
    @RequestMapping(path = "/updateNodeInfo")
    @ResponseBody
    public RestResult updateNodeInfo(@RequestParam int nodeId,
                                     @RequestParam String link,@RequestParam String description,
                                     @RequestParam String email,@RequestParam String phone,
                                     @RequestParam(defaultValue = "") String avatar,
                                     HttpSession session){
        User u =  (User)session.getAttribute(session.getId());
        if(u!=null){
            if(!"admin".equals(u.getRole())){
                if(nodeId!=u.getNodeId()){
                    log.error("nodeId:"+nodeId+", role:"+u.getRole());
                    return RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED,"Permission denied");
                }
            }
        }

        RestResult result = nodeService.updateNodeInfo(nodeId,link,description,email,phone,avatar);
        return result;
    }
    //删除节点
    @GetMapping(path = "/deleteNode")
    @ResponseBody
    public RestResult deleteNode(@RequestParam int nodeId,HttpSession session){
       User u = (User)session.getAttribute(session.getId());
        if(u.getNodeId()!=nodeId){
            log.error("Permission denied");
            return RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED,"Permission denied");
        }
        RestResult result = nodeService.deleteNode(nodeId);
       return result;
    }
    //获取节点
    @RequestMapping(path = "/nodes")
    @ResponseBody
    public RestResult getNodesByUser(HttpSession session){
        User u =  (User)session.getAttribute(session.getId());
        RestResult result = nodeService.getNodesByUser(u.getId());
        return result;
    }
    @RequestMapping(path = "/nodePass/{nodeId}")
    @ResponseBody
    public RestResult nodePass(@PathVariable("nodeId") int nodeId,HttpSession session){
        User u = (User)session.getAttribute(session.getId());
        if(!"admin".equals(u.getRole())){
            log.error("Permission denied");
            return RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED,"Permission denied");
        }
        RestResult result = nodeService.nodePass(nodeId);
        return result;
    }
    @RequestMapping(path = "/nodeReject/{nodeId}")
    @ResponseBody
    public RestResult nodeReject(@PathVariable("nodeId") int nodeId,HttpSession session){
        User u = (User)session.getAttribute(session.getId());
        if(!"admin".equals(u.getRole())){
            log.error("Permission denied");
            return RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED,"Permission denied");
        }
        RestResult result = nodeService.nodeReject(nodeId);
        return result;
    }
    //从api上获取并更新节点信息
    @RequestMapping(path = "/updateNodes")
    @ResponseBody
    public RestResult updateNodes(HttpSession session){
        User u = (User)session.getAttribute(session.getId());
        RestResult result = RestResult.resultOf(ErrorCode.ERROR_CODE_FAILED,"can't update nodes");
        if("admin".equals(u.getRole())){
            log.error("Permission denied");
            result = nodeService.updateNodesFromApi();
        }
        return result;
    }
}
