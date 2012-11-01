package com.orbitz.gradle.cobertura.tasks

import org.gradle.api.internal.ConventionMapping;

import org.gradle.api.internal.ConventionAwareHelper;
import org.gradle.api.internal.ConventionMapping;

import org.gradle.api.Action
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.internal.IConventionAware;

class InstrumentCodeAction implements Action<Task>, IConventionAware {
    
    File datafile
    File destinationDir
    Set<File> classesDirs
    Set<String> includes
    Set<String> excludes
    Set<String> ignores
    def runner
    private ConventionMapping conventionMapping
    
    def InstrumentCodeAction(Project project) {
        conventionMapping = new ConventionAwareHelper(this, project.getConvention())
    }

    def getInstrumentDir(File originalDir, Task task) {
        def subDir
        if (originalDir.absolutePath.startsWith(task.project.buildDir.absolutePath)) {
            subDir = originalDir.absolutePath.substring(task.project.buildDir.absolutePath.length())
        } else {
            subDir = originalDir.name
        }
        "${task.project.buildDir}/instrumented_classes/${subDir}" as String
    }
    
    void execute(Task task) {
        def instrumentDirs = [] as Set
        getClassesDirs().each { File f ->
            if (f.isDirectory()) {
                def instrumentDir = getInstrumentDir(f, task)
                task.project.copy {
                    into instrumentDir
                    from f
                }
                instrumentDirs << instrumentDir
            } else {
                instrumentDirs << f.path
            }
        }
        task.classpath = task.project.files(task.classpath.files.collect { File f ->
            getClassesDirs().contains(f) ? getInstrumentDir(f, task) : f
        })
        runner.instrument null, getDatafile().path, getDestinationDir()?.path, getIgnores() as List, getIncludes() as List,
                getExcludes() as List, instrumentDirs as List
    }
    
    void setConventionMapping(ConventionMapping mapping) {
        this.conventionMapping = mapping
    }
    
    ConventionMapping getConventionMapping() {
        return conventionMapping
    }
    
    
}
