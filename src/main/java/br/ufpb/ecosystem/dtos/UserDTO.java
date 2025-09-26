package br.ufpb.ecosystem.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Schema(description = "DTO representando os dados de usuário para registro e autenticação.")
public class UserDTO {

    @NotBlank(message = "O nome de usuário não pode estar em branco.")
    @Size(min = 4, max = 50, message = "O nome de usuário deve ter entre 4 e 50 caracteres.")
    @Schema(
            description = "Nome de usuário único para login",
            example = "joaosilva"
    )
    private String username;

    @NotBlank(message = "A senha não pode estar em branco.")
    @Size(min = 8, max = 255, message = "A senha deve ter entre 8 e 255 caracteres.")
    @Schema(
            description = "Senha para autenticação (mínimo de 8 caracteres).",
            example = "Senh4_TBjs&!"
    )
    private String password;

    @Enumerated(EnumType.STRING)
    @Schema(
            description = "Papéis do usuário. Pode conter múltiplos valores, como ADMIN ou USER.",
            example = "[\"ADMIN\", \"USER\"]"
    )
    private Set<String> role;

    public UserDTO() {}

    public UserDTO(String username, String password, Set<String> role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
