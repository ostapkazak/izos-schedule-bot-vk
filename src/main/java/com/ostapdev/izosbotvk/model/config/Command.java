package com.ostapdev.izosbotvk.model.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Command {
    private String name;
    private String description;
}
