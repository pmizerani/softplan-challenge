package com.softplan.challenge.service;

import com.softplan.challenge.dto.PessoaRequestDto;
import com.softplan.challenge.model.Pessoa;
import com.softplan.challenge.repository.PessoaRepository;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;
import reactor.test.StepVerifier;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PessoaServiceTest {

    @Autowired PessoaRepository pessoaRepository;

    @Autowired PessoaService pessoaService;

    private PessoaRequestDto pessoaRequestDto;

    @BeforeEach
    void beforeEach() {

        pessoaRequestDto = new PessoaRequestDto();
        pessoaRequestDto.setNome("teste");
        pessoaRequestDto.setEmail("teste@teste.com");
    }

    @Test
    @Order(1)
    void cadastrarPessoa_sucesso() {

        StepVerifier.create(pessoaService.cadastrarPessoa(pessoaRequestDto))
                .expectNextMatches(pessoaResponseDto -> {
                    assertEquals("teste", pessoaResponseDto.getNome());
                    assertEquals("teste@teste.com", pessoaResponseDto.getEmail());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void buscarPessoas_sucesso() {

        StepVerifier.create(pessoaService.buscarPessoas(0, 10))
                .expectNextMatches(pessoaResponseDto -> {
                    assertEquals("Novo nome", pessoaResponseDto.getNome());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void buscarPessoas_vazio_sucesso() {

        StepVerifier.create(pessoaService.buscarPessoas(1, 10))
                .expectErrorMatches(error -> {
                    assertEquals("204 NO_CONTENT", error.getMessage());
                    return true;
                })
                .verify();
    }

    @Test
    void buscarPessoaPorId_sucesso() {

        StepVerifier.create(pessoaService.buscarPessoaPorId(1l))
                .expectNextMatches(pessoaResponseDto -> {
                    assertEquals("Novo nome", pessoaResponseDto.getNome());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void buscarPessoaPorId_NaoEncontrado() {

        StepVerifier.create(pessoaService.buscarPessoaPorId(2l))
                .expectErrorMatches(error -> {
                    assertEquals("Pessoa não encontrada", ((ResponseStatusException) error).getReason());
                    return true;
                })
                .verify();
    }

    @Test
    @Order(2)
    void atualizarPessoaPorId_sucesso() {

        pessoaRequestDto.setNome("Novo nome");

        StepVerifier.create(pessoaService.atualizarPessoaPorId(1l, pessoaRequestDto))
                .expectNextMatches(pessoaResponseDto -> {
                    assertEquals("Novo nome", pessoaResponseDto.getNome());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void atualizarPessoaPorId_PessoaNaoEncontrada() {

        pessoaRequestDto.setNome("Novo nome");

        StepVerifier.create(pessoaService.atualizarPessoaPorId(2l, pessoaRequestDto))
                .expectErrorMatches(error -> {
                    assertEquals("Pessoa não encontrada", ((ResponseStatusException) error).getReason());
                    return true;
                })
                .verify();
    }

    @Test
    void excluirPessoaPorId_sucesso() {

        pessoaService.excluirPessoaPorId(1l);
        StepVerifier.create(pessoaService.buscarPessoaPorId(1l))
                .expectErrorMatches(error -> {
                    assertEquals("Pessoa não encontrada", ((ResponseStatusException) error).getReason());
                    return true;
                })
                .verify();
    }

}
