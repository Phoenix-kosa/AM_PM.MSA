package phoenix.AM_PM.ganttchartservice.task.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddTaskDTO {
    private int projectId;
    private String content;
}
