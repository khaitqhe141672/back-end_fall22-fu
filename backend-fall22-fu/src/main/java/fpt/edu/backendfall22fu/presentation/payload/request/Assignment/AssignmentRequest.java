package fpt.edu.backendfall22fu.presentation.payload.request.Assignment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentRequest {

    private String title;
    private String content;
    private Date deadline;

}
