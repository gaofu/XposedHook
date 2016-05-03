package gaofu.xposedhook.domain;

import java.util.List;

/**
 * Created by gaofu.
 * Created on 2016/5/2.
 */
public class HookClass {
    private String name;
    private String tag;
    private boolean constructor;
    private List<HookMethod> hookMethods;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HookMethod> getHookMethods() {
        return hookMethods;
    }

    public void setHookMethods(List<HookMethod> hookMethods) {
        this.hookMethods = hookMethods;
    }

    public boolean isConstructor() {
        return constructor;
    }

    public void setConstructor(boolean constructor) {
        this.constructor = constructor;
    }

    @Override
    public String toString() {
        return "HookClass{" +
                "tag='" + tag + '\'' +
                ", name='" + name + '\'' +
                ", constructor=" + constructor +
                ", hookMethods=" + hookMethods +
                '}';
    }
}
