package br.com.fiap.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "TB_USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "USUARIO_SEQ", allocationSize = 1)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Gasto> gastos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Renda> rendas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Investimento> investimentos;

    public Usuario() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<Gasto> getGastos() { return gastos; }
    public void setGastos(List<Gasto> gastos) { this.gastos = gastos; }
    public List<Renda> getRendas() { return rendas; }
    public void setRendas(List<Renda> rendas) { this.rendas = rendas; }
    public List<Investimento> getInvestimentos() { return investimentos; }
    public void setInvestimentos(List<Investimento> investimentos) { this.investimentos = investimentos; }
}