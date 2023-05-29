package com.softplan.challenge.controller;

import com.softplan.challenge.dto.PessoaRequestDto;
import com.softplan.challenge.dto.PessoaResponseDto;
import com.softplan.challenge.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/api/v1/pessoas", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Pessoa", description = "CRUD de pessoas")
public class PessoaController {

    @Autowired PessoaService pessoaService;

    @PostMapping()
    @ResponseStatus(CREATED)
    @ApiResponse(responseCode = "201", description = "Created")
    @Operation(summary = "Cadastrar nova pessoa", description = "Recurso responsável por cadastrar uma nova pessoa")
    public Mono<PessoaResponseDto> cadastrarPessoa(@Validated @RequestBody PessoaRequestDto pessoaRequestDto) {
        return pessoaService.cadastrarPessoa(pessoaRequestDto);
    }

    @GetMapping()
    @ApiResponse(responseCode = "200", description = "Ok")
    @ApiResponse(responseCode = "204", description = "No content")
    @Operation(summary = "Buscar pessoas", description = "Recurso responsável por buscar todas as pessoas cadastradas por paginação")
    public Flux<PessoaResponseDto> buscarPessoas(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam("size") Integer size) {
        return pessoaService.buscarPessoas(page, size);
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Ok")
    @Operation(summary = "Buscar pessoa por ID", description = "Recurso responsável por buscar uma pessoa por ID")
    public Mono<PessoaResponseDto> buscarPessoaPorId(@PathVariable Long id) {
        return pessoaService.buscarPessoaPorId(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pessoa", description = "Recurso responsável por atualizar os dados de uma pessoa")
    public Mono<PessoaResponseDto> atualizarPessoaPorId(@PathVariable Long id, @Validated @RequestBody PessoaRequestDto pessoaRequestDto) {
        return pessoaService.atualizarPessoaPorId(id, pessoaRequestDto);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Ok")
    @Operation(summary = "Excluir pessoa", description = "Recurso responsável por excluir uma pessoa")
    public Mono<Void> excluirPessoaPorId(@PathVariable Long id) {
        return pessoaService.excluirPessoaPorId(id);
    }

}
