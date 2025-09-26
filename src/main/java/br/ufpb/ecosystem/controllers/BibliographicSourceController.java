package br.ufpb.ecosystem.controllers;

import br.ufpb.ecosystem.dtos.BibliographicSourceDTO;
import br.ufpb.ecosystem.enums.BibliographicSourceEnum;
import br.ufpb.ecosystem.mappers.BibliographicSourceMapper;
import br.ufpb.ecosystem.security.SecurityConfig;
import br.ufpb.ecosystem.services.BibliographicSourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/library")
@Tag(name = "Library", description = "Controlador responsável pelo gerenciamento de dados de Fontes")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class BibliographicSourceController {

    private final BibliographicSourceService bibliographicSourceService;
    private final BibliographicSourceMapper mapper;

    @Autowired
    public BibliographicSourceController(BibliographicSourceService bibliographicSourceService, BibliographicSourceMapper mapper) {
        this.bibliographicSourceService = bibliographicSourceService;
        this.mapper = mapper;
    }


    // ---------------- CREATE ----------------
    @PostMapping("/add")
    @Operation(
            summary = "Adicionar uma nova fonte bibliográfica",
            description = "Cria uma nova fonte com os dados fornecidos"
    )
    @ApiResponse(responseCode = "200", description = "Fonte adicionada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(
            examples = @ExampleObject(value = "{ \"message\": \"Dados da requisição inválidos\" }")
    ))
    public ResponseEntity<?> insert(@Valid @RequestBody BibliographicSourceDTO dto) {
        try {
            BibliographicSourceDTO saved = bibliographicSourceService.insert(dto);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }


    // ---------------- LIST ----------------
    @GetMapping
    @Operation(
            summary = "Listar todas as fontes",
            description = "Retorna uma lista paginada com todas as fontes cadastradas"
    )
    public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<BibliographicSourceDTO> works = bibliographicSourceService.findAll(page, size);
        if (works.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(Map.of("message", "Nenhuma fonte bibliográfica encontrada"));
        }
        return ResponseEntity.ok(works);
    }


    // ---------------- FIND BY ID ----------------
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar fonte pelo ID",
            description = "Retorna os dados da fonte correspondente ao ID informado"
    )
    @ApiResponse(responseCode = "200", description = "Fonte encontrada")
    @ApiResponse(responseCode = "404", description = "Fonte não encontrada", content = @Content(
            examples = @ExampleObject(value = "{ \"message\": \"Fonte bibliográfica não encontrada\" }")
    ))
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bibliographicSourceService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Fonte não encontrada"));
        }
    }


    // ---------------- UPDATE ----------------
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar fonte pelo ID",
            description = "Atualiza todos os campos da fonte com o ID informado"
    )
    @ApiResponse(responseCode = "200", description = "Fonte atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Fonte não encontrada", content = @Content(
            examples = @ExampleObject(value = "{ \"message\": \"Fonte não encontrada\" }")
    ))
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody BibliographicSourceDTO dto) {
        try {
            return ResponseEntity.ok(bibliographicSourceService.update(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Fonte não encontrada"));
        }
    }


    // ---------------- PARTIAL UPDATE ----------------
    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar parcialmente fonte pelo ID",
            description = "Atualiza apenas os campos fornecidos da fonte com o ID informado"
    )
    @ApiResponse(responseCode = "200", description = "Fonte parcialmente atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Fonte não encontrada", content = @Content(
            examples = @ExampleObject(value = "{ \"message\": \"Fonte não encontrada\" }")
    ))
    public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody BibliographicSourceDTO dto) {
        try {
            return ResponseEntity.ok(bibliographicSourceService.partialUpdate(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Fonte não encontrada"));
        }
    }


    // ---------------- DELETE ----------------
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir fonte pelo ID",
            description = "Remove a fonte correspondente ao ID informado"
    )
    @ApiResponse(responseCode = "200", description = "Fonte excluída com sucesso")
    @ApiResponse(responseCode = "404", description = "Fonte não encontrada", content = @Content(
            examples = @ExampleObject(value = "{ \"message\": \"Fonte não encontrada\" }")
    ))
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            bibliographicSourceService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(Map.of("message", "Fonte excluída com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Fonte não encontrada"));
        }
    }

    // ---------------- SEARCH ----------------
    @GetMapping("/search")
    @Operation(
            summary = "Pesquisar fontes bibliográficas por filtros",
            description = "Permite filtrar fontes por título, autor, ano, tipo, mídia ou cidade de pesquisa"
    )
    public ResponseEntity<Page<BibliographicSourceDTO>> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer yearStart,
            @RequestParam(required = false) Integer yearEnd,
            @RequestParam(required = false) BibliographicSourceEnum.Type type,
            @RequestParam(required = false) BibliographicSourceEnum.Media media,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<BibliographicSourceDTO> results = bibliographicSourceService.search(
                title, author, yearStart, yearEnd, type, media, page, size
        ).map(mapper::toDto);

        return ResponseEntity.ok(results);
    }
}