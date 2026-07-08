package br.com.senac.projeto_spring_aula.saudacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
// caso precise ter muitos argumentos no construtor e reduzir código
@RequiredArgsConstructor
public class SaudacaoService {

    private final Saudacao saudacao;

    public String executar(){
        return saudacao.mensagem();
    }

}
