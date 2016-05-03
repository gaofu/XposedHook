package gaofu.xposedhook.domain;

import java.util.List;

/**
 * Created by gaofu.
 * Created on 2016/5/2.
 */
public class HookPackage {
    private String name;
    private List<HookClass> hookClasses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HookClass> getHookClasses() {
        return hookClasses;
    }

    public void setHookClasses(List<HookClass> hookClasses) {
        this.hookClasses = hookClasses;
    }

    @Override
    public String toString() {
        return "HookPackage{" +
                "name='" + name + '\'' +
                ", hookClasses=" + hookClasses +
                '}';
    }
}
