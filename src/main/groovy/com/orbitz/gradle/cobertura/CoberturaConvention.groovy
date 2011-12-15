package com.orbitz.gradle.cobertura


import org.gradle.api.Project;

/**
 * Convention class for Cobertura plugin
 *
 * Copyright 2011 Orbitz, LLC
 */
class CoberturaConvention {
    
    /**
     * Directories under the base directory containing classes to be instrumented. Defaults to [project.sourceSets.main.classesDir.path]
     */
    List<String> coverageDirs
    
    /**
     * Path to data file to use for Cobertura. Defaults to ${project.buildDir.path}/cobertura/cobertura.ser
     */
    File coverageDatafile
    
    /**
     * Path to report directory for coverage report. Defaults to ${project.reportsDir.path}/cobertura
     */
    File coverageReportDir
    
    /**
     * Format of cobertura report. Default is 'html'
     */
    String coverageFormat = 'html'
    
    /**
     * Directories of source files to use. Defaults to project.sourceSets.main.java.srcDirs
     */
    Set<File> coverageSourceDirs
    
    /**
     * List of include patterns
     */
    List<String> coverageIncludes
    
    /**
     * List of exclude patterns
     */
    List<String> coverageExcludes

	/**
     * List of ignore patterns
     */
    List<String> coverageIgnores
        
    /**
     * Version of cobertura to use for the plugin. Defaults to 1.9.4.1
     */
    String coberturaVersion = '1.9.4.1'
    
    private Project project
    
    CoberturaConvention(Project project) {
        this.project = project
        coverageDirs = [ project.sourceSets.main.classesDir.path ]
        coverageDatafile = new File("${project.buildDir.path}/cobertura", 'cobertura.ser')
        coverageReportDir = new File("${project.reportsDir.path}/cobertura")
        coverageSourceDirs = project.sourceSets.main.java.srcDirs
    }
    
    def cobertura(Closure closure) {
        closure.setDelegate this
        closure.call()
    }
    
    def setCoverageDataFile(String fileName) {
        coverageDatafile = new File(fileName)
    }
    
    def setCoverageReportDir(String reportDirName) {
        coverageReportDir = new File(reportDirName)
    }
    
}
