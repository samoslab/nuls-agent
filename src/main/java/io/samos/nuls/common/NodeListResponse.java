package io.samos.nuls.common;

import io.samos.nuls.entity.Node;

import java.util.List;

public class NodeListResponse {
    public class Result{
        private int pageNumber;
        private int pagesize;
        private int totalCount;
        private List<Node> list;

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public int getPagesize() {
            return pagesize;
        }

        public void setPagesize(int pagesize) {
            this.pagesize = pagesize;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<Node> getList() {
            return list;
        }

        public void setList(List<Node> list) {
            this.list = list;
        }
    }
    private String jsonrpc;
    private int id;
    private Result result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
