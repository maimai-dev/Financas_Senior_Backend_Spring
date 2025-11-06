package br.com.fiap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Table(name = "TB_INVESTIMENTO")
public class Investimento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invest_seq")
    @SequenceGenerator(name = "invest_seq", sequenceName = "INVEST_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O tipo de investimento é obrigatório")
    private String tipoInvestimento;

    @NotNull(message = "O valor aplicado é obrigatório")
    @Positive(message = "O valor deve ser positivo")
    private Double valorAplicado;

    @NotNull(message = "A data da aplicação é obrigatória")
    @PastOrPresent
    private LocalDate dataAplicacao;

    private String corretora;

    public Investimento() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipoInvestimento() { return tipoInvestimento; }
    public void setTipoInvestimento(String tipoInvestimento) { this.tipoInvestimento = tipoInvestimento; }
    public Double getValorAplicado() { return valorAplicado; }
    public void setValorAplicado(Double valorAplicado) { this.valorAplicado = valorAplicado; }
    public LocalDate getDataAplicacao() { return dataAplicacao; }
    public void setDataAplicacao(LocalDate dataAplicacao) { this.dataAplicacao = dataAplicacao; }
    public String getCorretora() { return corretora; }
    public void setCorretora(String corretora) { this.corretora = corretora; }
}