package br.com.forum_hub.domain.autenticacao;

public record DadosToken(String tokenAcesso, Object refreshToken) {
}
