package phoenix.AM_PM.mainservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "planservice", url = "http://localhost:8003")
public interface ProjectPlanServiceClient {
    @PostMapping("/project-plans/default")
    void createDefaultProjectPlans(@RequestParam("projectId") Integer projectId);
}