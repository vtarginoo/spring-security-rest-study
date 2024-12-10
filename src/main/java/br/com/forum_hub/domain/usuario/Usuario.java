package br.com.forum_hub.domain.usuario;

import br.com.forum_hub.infra.exception.RegraDeNegocioException;
import jakarta.persistence.*;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name="usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    private String email;
    private String senha;
    private String nomeUsuario;
    private String biografia;
    private String miniBiografia;
    private Boolean verificado;
    private String token;
    private LocalDateTime expiracaoToken;
    private Boolean ativo;

    @Deprecated
    public Usuario(){}

    public Usuario(DadosCadastroUsuario dados, String senhaCriptografada) {
        this.nomeCompleto = dados.nomeCompleto();
        this.email = dados.email();
        this.senha = senhaCriptografada;
        this.nomeUsuario = dados.nomeUsuario();
        this.biografia = dados.biografia();
        this.miniBiografia = dados.miniBiografia();
        this.verificado = false;
        this.token = UUID.randomUUID().toString();
        this.expiracaoToken = LocalDateTime.now().plusMinutes(30);
        this.ativo = false;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    public Long getId() {return id;}
    @Override
    public String getUsername() {
        return email;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getBiografia() {
        return biografia;
    }

    public String getMiniBiografia() {
        return miniBiografia;
    }

    public String getToken() {
        return token;
    }

    public void verificar() {
        if(expiracaoToken.isBefore(LocalDateTime.now())){
            throw new RegraDeNegocioException("Link de verificação expirou!");
        }
        this.verificado = true;
        this.ativo = true;
        this.token = null;
        this.expiracaoToken = null;
    }


}
