package br.ufpb.ecosystem.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta retornada quando ocorre um erro.")
public class ErrorResponseDTO {

    @Schema(
            description = "Mensagem de erro descrevendo o que deu errado.",
            example = "Usuário ou senha inválidos"
    )
    private String message;

    public ErrorResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
