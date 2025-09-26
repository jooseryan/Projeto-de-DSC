package br.ufpb.ecosystem.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta contendo o token JWT retornado após autenticação bem-sucedida.")
public class LoginResponseDTO {

    @Schema(
            description = "Token JWT que deve ser utilizado no cabeçalho Authorization para acessar endpoints protegidos.",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    )
    private String token;

    public LoginResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
