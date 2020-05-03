package com.example.ppmtool.services;

import com.example.ppmtool.domain.Backlog;
import com.example.ppmtool.domain.ProjectTask;
import com.example.ppmtool.repositories.BacklogRepository;
import com.example.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        // ProjectTask to be added to a specific project, project != null, backlog exists
        Backlog backlog = backlogRepository.findByProjectIdentifier((projectIdentifier));
        // set the Backlog to ProjectTask
        projectTask.setBacklog(backlog);
        // keep the project sequence to be continuously growing
        Integer BacklogSequence = backlog.getPTSequence();
        // update the Backlog Sequence
        BacklogSequence++;
        backlog.setPTSequence(BacklogSequence);
        // add Backlog Sequence to Project Task
        projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        // initial priority when priority null
        if(projectTask.getPriority()==null) {
            projectTask.setPriority(3);
        }

        // initial status is null
        if(projectTask.getStatus()=="" || projectTask.getStatus()==null) {
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String id) {
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }
}
