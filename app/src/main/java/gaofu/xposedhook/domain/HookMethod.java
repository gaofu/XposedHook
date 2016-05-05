package gaofu.xposedhook.domain;

import java.util.List;
import java.util.Map;

/**
 * Created by gaofu.
 * Created on 2016/5/2.
 */
public class HookMethod {
    private String name;
    private List<String> params;
    private Map<String, String> resultFields;

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

    public Map<String, String> getResultFields() {
        return resultFields;
    }

    public void setResultFields(Map<String, String> resultFields) {
        this.resultFields = resultFields;
    }

    @Override
    public String toString() {
        return "HookMethod{" +
                "name='" + name + '\'' +
                ", params=" + params +
                ", resultFields=" + resultFields +
                '}';
    }
}
