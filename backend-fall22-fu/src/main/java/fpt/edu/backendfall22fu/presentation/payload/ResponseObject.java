package fpt.edu.backendfall22fu.presentation.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {
    private String message;
    private Map<String,Object> data;
}