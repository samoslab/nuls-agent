package io.samos.nuls.entity;

import org.junit.ClassRule;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/*
    节点操作日志
 */
@Entity
public class Oplog implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)

    private Integer id;
    private Integer user_id;
    private String op_code;
    @Column(length = 2048)
    private String info;
    private Date createTime;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getOp_code() {
        return op_code;
    }

    public void setOp_code(String op_code) {
        this.op_code = op_code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
