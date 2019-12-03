package io.samos.nuls.entity;

import javax.persistence.*;
import java.io.Serializable;

/*
    节点展示信息
 */
@Entity
public class Node implements Serializable {
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        @Column(name = "id",unique = true,nullable = false)
        private int id;
        @Column(length = 1024)
        private String link;
        @Column(length = 4096)
        private String description;
        private String email;
        private String phone;
        private String avatar;
        private int checkstatus;//0 已审核 1审核中 2 审核不通过 3已删除
        private String txHash;
        private String agentId;
        private String agentAddress;
        private String packingAddress;
        private String rewardAddress;
        private String agentAlias;
        private long deposit;
        private int commissionRate;
        private long createTime;
        private long blockHeight;
        private int status;
        private long totalDeposit;
        private int depositCount;
        private float creditValue;
        private int totalPackingCount;
        private float lostRate;
        private long totalReward;
        private long commissionReward;
        private long agentReward;
        private long roundPackingTime;
        private int type;
        private int lastRewardHeight;
        private long deleteHash;
        private int version;
        private boolean New;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getCheckstatus() {
        return checkstatus;
    }

    public void setCheckstatus(int checkstatus) {
        this.checkstatus = checkstatus;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentAddress() {
        return agentAddress;
    }

    public void setAgentAddress(String agentAddress) {
        this.agentAddress = agentAddress;
    }

    public String getPackingAddress() {
        return packingAddress;
    }

    public void setPackingAddress(String packingAddress) {
        this.packingAddress = packingAddress;
    }

    public String getRewardAddress() {
        return rewardAddress;
    }

    public void setRewardAddress(String rewardAddress) {
        this.rewardAddress = rewardAddress;
    }

    public String getAgentAlias() {
        return agentAlias;
    }

    public void setAgentAlias(String agentAlias) {
        this.agentAlias = agentAlias;
    }

    public long getDeposit() {
        return deposit;
    }

    public void setDeposit(long deposit) {
        this.deposit = deposit;
    }

    public int getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(int commissionRate) {
        this.commissionRate = commissionRate;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(long blockHeight) {
        this.blockHeight = blockHeight;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(long totalDeposit) {
        this.totalDeposit = totalDeposit;
    }

    public int getDepositCount() {
        return depositCount;
    }

    public void setDepositCount(int depositCount) {
        this.depositCount = depositCount;
    }

    public float getCreditValue() {
        return creditValue;
    }

    public void setCreditValue(float creditValue) {
        this.creditValue = creditValue;
    }

    public int getTotalPackingCount() {
        return totalPackingCount;
    }

    public void setTotalPackingCount(int totalPackingCount) {
        this.totalPackingCount = totalPackingCount;
    }

    public int getLastRewardHeight() {
        return lastRewardHeight;
    }

    public void setLastRewardHeight(int lastRewardHeight) {
        this.lastRewardHeight = lastRewardHeight;
    }

    public long getDeleteHash() {
        return deleteHash;
    }

    public void setDeleteHash(long deleteHash) {
        this.deleteHash = deleteHash;
    }



    public float getLostRate() {
        return lostRate;
    }

    public void setLostRate(float lostRate) {
        this.lostRate = lostRate;
    }

    public long getTotalReward() {
        return totalReward;
    }

    public void setTotalReward(long totalReward) {
        this.totalReward = totalReward;
    }

    public long getCommissionReward() {
        return commissionReward;
    }

    public void setCommissionReward(long commissionReward) {
        this.commissionReward = commissionReward;
    }

    public long getAgentReward() {
        return agentReward;
    }

    public void setAgentReward(long agentReward) {
        this.agentReward = agentReward;
    }

    public long getRoundPackingTime() {
        return roundPackingTime;
    }

    public void setRoundPackingTime(long roundPackingTime) {
        this.roundPackingTime = roundPackingTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isNew() {
        return New;
    }

    public void setNew(boolean aNew) {
        New = aNew;
    }
}
