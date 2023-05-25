package com.softplan.challenge.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PessoaRequestDto {

    @NotEmpty
    private String nome;

    @NotEmpty
    @Email
    private String email;

}
