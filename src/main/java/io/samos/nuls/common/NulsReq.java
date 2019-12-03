package io.samos.nuls.common;

public class NulsReq {
   // { "jsonrpc":"2.0", "method":"getConsensusNodes", "params":[1,10,0], "id":1234 }

    private String jsonrpc;
    private String method;
    private Integer[] params;
    private Integer id;
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public NulsReq(String jsonrpc, String method, Integer[] params, Integer id) {
        this.jsonrpc = jsonrpc;
        this.method = method;
        this.params = params;
        this.id = id;
    }
    public NulsReq(){}

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer[] getParams() {
        return params;
    }

    public void setParams(Integer[] params) {
        this.params = params;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
