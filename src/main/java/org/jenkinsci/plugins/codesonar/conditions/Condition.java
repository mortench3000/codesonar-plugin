package org.jenkinsci.plugins.codesonar.conditions;

import hudson.AbortException;
import hudson.DescriptorExtensionList;
import hudson.ExtensionPoint;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Result;
import jenkins.model.Jenkins;

/**
 *
 * @author andrius
 */
public abstract class Condition implements Describable<Condition>, ExtensionPoint {

    public abstract Result validate(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws AbortException;
    
    @Override
    public Descriptor<Condition> getDescriptor() {
        Jenkins instance = Jenkins.getInstance();

        if (instance != null) {
            return (ConditionDescriptor<?>) instance.getDescriptorOrDie(getClass());
        }
            
        throw new NullPointerException("Jenkins is not started or is stopped");
    }
    
    public static DescriptorExtensionList<Condition, ConditionDescriptor<Condition>> getAll() {
        Jenkins instance = Jenkins.getInstance();

        if (instance != null) {
            return instance.<Condition, ConditionDescriptor<Condition>>getDescriptorList(Condition.class);
        }
        
        throw new NullPointerException("Jenkins is not started or is stopped");
    }
}
