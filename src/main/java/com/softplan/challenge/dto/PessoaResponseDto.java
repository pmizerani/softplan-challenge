package com.softplan.challenge.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class PessoaResponseDto {

    private Long id;

    private String nome;

    private String email;

    private Date dataCriacao;

    private Date dataUltimaAlteracao;

}
