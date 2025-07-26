package org.example.myportfolio.response;

import lombok.Data;
import org.example.myportfolio.model.Level;

@Data
public class SkillsResponse {
    private Long id;
    private String name;
    private Level level;

}
