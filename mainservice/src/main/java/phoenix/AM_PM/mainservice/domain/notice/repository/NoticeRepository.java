package phoenix.AM_PM.mainservice.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phoenix.AM_PM.mainservice.domain.notice.entity.Notice;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, String> {
    void deleteById(int id);
    Notice findById(int id);
    List<Notice> findByProjectId(int projectId);
}
