package br.com.senac.projeto_spring_aula.saudacao;

import org.springframework.stereotype.Component;

@Component
public class Saudacao {
    public String mensagem(){
        return "Olá eu sou o Bean gerenciado pelo String!";
    }

}
