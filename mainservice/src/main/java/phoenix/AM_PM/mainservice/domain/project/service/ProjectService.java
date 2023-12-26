package phoenix.AM_PM.mainservice.domain.project.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import phoenix.AM_PM.mainservice.domain.members.entity.Members;
import phoenix.AM_PM.mainservice.domain.members.entity.Roles;
import phoenix.AM_PM.mainservice.domain.members.repository.MembersRepository;
import phoenix.AM_PM.mainservice.domain.project.dto.RequestProject;
import phoenix.AM_PM.mainservice.domain.project.dto.ResponseProject;
import phoenix.AM_PM.mainservice.domain.project.entity.Project;
import phoenix.AM_PM.mainservice.domain.project.repository.ProjectRepository;
import phoenix.AM_PM.mainservice.domain.user.entity.User;
import phoenix.AM_PM.mainservice.domain.user.repository.UserRepository;
import phoenix.AM_PM.mainservice.global.exception.BusinessLogicException;
import phoenix.AM_PM.mainservice.global.exception.ExceptionCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MembersRepository memberRepository;
    private final UserRepository userRepository;

    ProjectService(ProjectRepository projectRepository, MembersRepository memberRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    public List<ResponseProject> getProjectList(Integer userId) {
        List<ResponseProject> projectList = new ArrayList<>();
        memberRepository.findAllByUserId(userId).stream().forEach(members -> projectList.add(ResponseProject.of(members.getProject())));
        return projectList;
    }

    public ResponseProject readProject(Integer projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(()->new BusinessLogicException(ExceptionCode.PROJECT_NOT_FOUND));
        return ResponseProject.of(project);
    }

    public ResponseProject createProject(RequestProject requestProject, User user) {
        Project entity = Project.from(requestProject);
        Project project = projectRepository.save(entity);

        Members member = new Members().builder()
                .user(user)
                .project(project)
                .roles(Roles.representative_member)
                .build();
        memberRepository.save(member);

        return ResponseProject.of(project);
    }

    @Transactional
    public void modifyProject(Integer projectId, RequestProject requestProject, User user) {
        Project project = projectRepository.findById(projectId).orElseThrow(()->new BusinessLogicException(ExceptionCode.PROJECT_NOT_FOUND));
        Members members = memberRepository.findAllByUserIdAndProjectId(user.getId(), projectId).orElseThrow(()->new BusinessLogicException(ExceptionCode.PROJECT_NOT_FOUND));
        if(members.getRoles() != Roles.representative_member)
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        Optional.ofNullable(requestProject.getTitle()).ifPresent(project::updateTitle);
        Optional.ofNullable(requestProject.getContent()).ifPresent(project::updateContent);
        Optional.ofNullable(requestProject.getStartDate()).ifPresent(project::updateStartDate);
        Optional.ofNullable(requestProject.getEndDate()).ifPresent(project::updateEndDate);
    }
}
