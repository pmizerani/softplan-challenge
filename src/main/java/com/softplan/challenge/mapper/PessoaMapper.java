package com.softplan.challenge.mapper;

import com.softplan.challenge.dto.PessoaRequestDto;
import com.softplan.challenge.dto.PessoaResponseDto;
import com.softplan.challenge.model.Pessoa;
import org.springframework.stereotype.Component;

@Component
public class PessoaMapper {

    private PessoaMapper() {
    }

    public static Pessoa pessoaRequesDtoToPessoa(PessoaRequestDto pessoaRequestDto) {
        return Pessoa
                .builder()
                .nome(pessoaRequestDto.getNome())
                .email(pessoaRequestDto.getEmail())
                .build();
    }

    public static Pessoa pessoaRequesDtoToPessoaUpdate(Long id, PessoaRequestDto pessoaRequestDto) {
        return Pessoa
                .builder()
                .id(id)
                .nome(pessoaRequestDto.getNome())
                .email(pessoaRequestDto.getEmail())
                .build();
    }

    public static PessoaResponseDto pessoaToPessoaResponseDto(Pessoa pessoa) {
        return PessoaResponseDto
                .builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .email(pessoa.getEmail())
                .dataCriacao(pessoa.getCreatedDate())
                .dataUltimaAlteracao(pessoa.getLastModifiedDate())
                .build();
    }
}
