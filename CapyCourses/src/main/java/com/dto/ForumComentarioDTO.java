package com.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForumComentarioDTO {
    private String data;
    private String usuario;
    private String comentario;
}
