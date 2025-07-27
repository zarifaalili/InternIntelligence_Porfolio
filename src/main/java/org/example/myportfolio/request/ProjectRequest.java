package org.example.myportfolio.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectRequest {
    @NotNull(message = "Title is required")
    private String title;
    @NotNull(message = "Description is required")
    private String description;
    @NotNull(message = "Link is required")
    private String link;
}
