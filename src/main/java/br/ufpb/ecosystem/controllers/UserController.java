package br.ufpb.ecosystem.controllers;

import br.ufpb.ecosystem.dtos.ErrorResponseDTO;
import br.ufpb.ecosystem.dtos.LoginResponseDTO;
import br.ufpb.ecosystem.dtos.UserDTO;
import br.ufpb.ecosystem.entities.User;
import br.ufpb.ecosystem.security.SecurityConfig;
import br.ufpb.ecosystem.security.TokenService;
import br.ufpb.ecosystem.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "Controlador responsável pela autenticação e gerenciamento de usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class UserController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public UserController(UserService userService,
                          TokenService tokenService,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    // ---------------- LOGIN ----------------
    @PostMapping("/login")
    @Operation(
            summary = "Autenticação de usuário",
            description = "Autentica um usuário com base nas credenciais fornecidas e retorna um token JWT em caso de sucesso"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Usuário autenticado com sucesso",
            content = @Content(examples = @ExampleObject(value = "{ \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI...\" }"))
    )
    @ApiResponse(
            responseCode = "401",
            description = "Credenciais inválidas",
            content = @Content(examples = @ExampleObject(value = "{ \"message\": \"Usuário ou senha inválidos\" }"))
    )
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
        );

        User user = userService.loginUser(userDTO);
        String token = tokenService.generateToken(user);

        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponseDTO("Usuário ou senha inválidos"));
        }
    }


    // ---------------- REGISTER ----------------
    @PostMapping("/register")
    @Operation(
            summary = "Registrar novo usuário",
            description = "Registra um novo usuário no sistema com base nas informações fornecidas"
    )
    @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso")
    @ApiResponse(
            responseCode = "400",
            description = "Erro de validação ou usuário já existente",
            content = @Content(examples = @ExampleObject(value = "{ \"message\": \"Usuário já existe ou dados inválidos\" }"))
    )
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            userService.registerUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(e.getMessage()));
        }
    }


    // ---------------- LIST USERS ----------------
    @GetMapping
    @Operation(
            summary = "Listar todos os usuários",
            description = "Retorna uma lista de todos os usuários cadastrados no sistema"
    )
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.listAllUsers());
    }


    // ---------------- GET USER BY ID ----------------
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar usuário pelo ID",
            description = "Retorna os dados do usuário correspondente ao ID informado"
    )
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(
            responseCode = "404",
            description = "Usuário não encontrado",
            content = @Content(examples = @ExampleObject(value = "{ \"message\": \"Usuário não encontrado\" }"))
    )
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO("Usuário não encontrado"));
        }
    }


    // ---------------- UPDATE USER ----------------
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar todos os dados do usuário",
            description = "Atualiza completamente os dados do usuário com base nas informações fornecidas"
    )
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(
            responseCode = "404",
            description = "Usuário não encontrado",
            content = @Content(examples = @ExampleObject(value = "{ \"message\": \"Usuário não encontrado\" }"))
    )
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, userDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO("Usuário não encontrado"));
        }
    }


    // ---------------- PARTIAL UPDATE ----------------
    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar parcialmente dados do usuário",
            description = "Atualiza apenas os campos fornecidos do usuário com base nas informações fornecidas"
    )
    @ApiResponse(responseCode = "200", description = "Usuário parcialmente atualizado com sucesso")
    @ApiResponse(
            responseCode = "404",
            description = "Usuário não encontrado",
            content = @Content(examples = @ExampleObject(value = "{ \"message\": \"Usuário não encontrado\" }"))
    )
    public ResponseEntity<?> partialUpdateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok(userService.updateUserPartially(id, userDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO("Usuário não encontrado"));
        }
    }


    // ---------------- DELETE USER ----------------
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir usuário",
            description = "Remove o usuário correspondente ao ID informado"
    )
    @ApiResponse(responseCode = "200", description = "Usuário excluído com sucesso")
    @ApiResponse(
            responseCode = "404",
            description = "Usuário não encontrado",
            content = @Content(examples = @ExampleObject(value = "{ \"message\": \"Usuário não encontrado\" }"))
    )
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("Usuário excluído com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO("Usuário não encontrado"));
        }
    }
}
