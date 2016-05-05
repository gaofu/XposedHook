package gaofu.xposedhook.domain;

import java.util.List;

/**
 * Created by gaofu.
 * Created on 2016/5/2.
 */
public class HookMethod {
    private String name;
    private List<String> params;
    private boolean printStackTrace;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public boolean isPrintStackTrace() {
        return printStackTrace;
    }

    public void setPrintStackTrace(boolean printStackTrace) {
        this.printStackTrace = printStackTrace;
    }

    @Override
    public String toString() {
        return "HookMethod{" +
                "name='" + name + '\'' +
                ", printStackTrace=" + printStackTrace +
                ", params=" + params +
                '}';
    }
}
