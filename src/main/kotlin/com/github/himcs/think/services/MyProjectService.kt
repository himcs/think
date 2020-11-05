package com.github.himcs.think.services

import com.intellij.openapi.project.Project
import com.github.himcs.think.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
