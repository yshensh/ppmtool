package com.example.ppmtool.services;

import com.example.ppmtool.domain.Backlog;
import com.example.ppmtool.domain.Project;
import com.example.ppmtool.domain.ProjectTask;
import com.example.ppmtool.exceptions.ProjectIdException;
import com.example.ppmtool.exceptions.ProjectNotFoundException;
import com.example.ppmtool.repositories.BacklogRepository;
import com.example.ppmtool.repositories.ProjectRepository;
import com.example.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        try {
            // ProjectTask to be added to a specific project, project != null, backlog exists
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            // set the Backlog to ProjectTask
            projectTask.setBacklog(backlog);
            // keep the project sequence to be continuously growing
            Integer BacklogSequence = backlog.getPTSequence();
            // update the Backlog Sequence
            BacklogSequence++;
            backlog.setPTSequence(BacklogSequence);
            // add Backlog Sequence to Project Task
            projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            // initial priority when priority null
            if (projectTask.getPriority() == null) {
                projectTask.setPriority(3);
            }

            // initial status is null
            if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project not found");
        }
    }

    public Iterable<ProjectTask> findBacklogById(String id) {

        Project project = projectRepository.findByProjectIdentifier(id.toUpperCase());

        if (project==null) {
            throw new ProjectNotFoundException("Project with id '" + id + "' does not exit");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {

        // make sure we are searching on the right backlog

        return projectTaskRepository.findByProjectSequence(pt_id);

    }
}
