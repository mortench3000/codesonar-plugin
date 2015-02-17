/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jenkinsci.plugins.codesonar;

import hudson.Extension;
import hudson.Launcher;
import hudson.Proc;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;
import hudson.util.ArgumentListBuilder;
import java.io.File;
import java.io.IOException;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 *
 * @author andrius
 */
public class CodeSonarPublisher extends Recorder {

    private final String PROJECT_NAME = "project_x";
    private final File WORKING_DIR = new File("/home/andrius/projects/codesonar-plugin/codesonar/", PROJECT_NAME);
    private final String SERVER_ADDRESS = "10.10.1.125:8080";

    @DataBoundConstructor
    public CodeSonarPublisher() {
    }

    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
        ArgumentListBuilder argumentListBuilder = new ArgumentListBuilder();
        argumentListBuilder.add("codesonar");
        argumentListBuilder.add("analyze");
        argumentListBuilder.add("/home/andrius/projects/codesonar-plugin/codesonar/" + PROJECT_NAME);
        argumentListBuilder.add("-foreground");
        argumentListBuilder.add(SERVER_ADDRESS);

        int result = launcher.launch()
                             .pwd(build.getWorkspace())
                             .cmds(argumentListBuilder)
                             .stdout(listener).join();

        if (result == 0) return false;
        
    }
    
    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }
    
    @Extension
    public static class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> type) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Code Sonar";
        }
    }
    
}
