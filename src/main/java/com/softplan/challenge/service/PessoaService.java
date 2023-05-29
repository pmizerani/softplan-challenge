package com.softplan.challenge.service;

import com.softplan.challenge.dto.PessoaRequestDto;
import com.softplan.challenge.dto.PessoaResponseDto;
import com.softplan.challenge.mapper.PessoaMapper;
import com.softplan.challenge.model.Pessoa;
import com.softplan.challenge.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class PessoaService {

    @Autowired PessoaRepository pessoaRepository;

    public Mono<PessoaResponseDto> cadastrarPessoa(PessoaRequestDto pessoaRequestDto) {
        return Mono.just(PessoaMapper.pessoaToPessoaResponseDto(pessoaRepository.save(PessoaMapper.pessoaRequesDtoToPessoa(pessoaRequestDto))));
    }

    public Flux<PessoaResponseDto> buscarPessoas(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Pessoa> listaPessoa = pessoaRepository.findAll(pageable);
        return Flux.fromIterable(listaPessoa.getContent()).map(PessoaMapper::pessoaToPessoaResponseDto)
                .switchIfEmpty(Flux.error(new ResponseStatusException(HttpStatus.NO_CONTENT)));
    }

    public Mono<PessoaResponseDto> buscarPessoaPorId(Long id) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        if (pessoaOptional.isEmpty()) return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));
        return Mono.just(PessoaMapper.pessoaToPessoaResponseDto(pessoaOptional.get()));
    }

    public Mono<Void> excluirPessoaPorId(Long id) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        if (pessoaOptional.isEmpty()) return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));
        pessoaRepository.deleteById(id);
        return Mono.empty();
    }

    public Mono<PessoaResponseDto> atualizarPessoaPorId(Long id, PessoaRequestDto pessoaRequestDto) {

        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        if (pessoaOptional.isEmpty()) return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));

        Pessoa pessoa = pessoaOptional.get();
        pessoa.setNome(pessoaRequestDto.getNome());
        pessoa.setEmail(pessoaRequestDto.getEmail());
        return Mono.just(PessoaMapper.pessoaToPessoaResponseDto(pessoaRepository.save(pessoa)));

    }
}
