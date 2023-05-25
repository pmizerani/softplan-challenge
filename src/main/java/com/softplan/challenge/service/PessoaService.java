package com.softplan.challenge.service;

import com.softplan.challenge.dto.PessoaRequestDto;
import com.softplan.challenge.dto.PessoaResponseDto;
import com.softplan.challenge.mapper.PessoaMapper;
import com.softplan.challenge.model.Pessoa;
import com.softplan.challenge.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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
        return Mono.justOrEmpty(pessoaRepository.findById(id)).map(PessoaMapper::pessoaToPessoaResponseDto)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada")));
    }

    public void excluirPessoaPorId(Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isPresent()) pessoaRepository.deleteById(id);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada");
    }
}
