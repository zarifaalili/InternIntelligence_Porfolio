package org.example.myportfolio.request;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.myportfolio.model.Level;

@Data
public class SkillsRequest {
    @NotNull(message = "Skill name is required")
    private String name;
    @NotNull(message = "Skill level is required")
    private Level level;

}