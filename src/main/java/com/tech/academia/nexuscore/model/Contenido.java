package com.tech.academia.nexuscore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tech.academia.nexuscore.model.enums.TipoContenido;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"modulo", "progresoContenidos"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contenido")
public class Contenido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String titulo;

  @Enumerated(EnumType.STRING)
  @Column(length = 50, nullable = false)
  private TipoContenido tipo;

  @Column(name = "url_archivo")
  private String urlArchivo;

  @Column(name = "duracion_minutos")
  private Integer duracionMinutos;

  @JsonBackReference("modulo-contenidos")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modulo_id", nullable = false)
  private Modulo modulo;

  @JsonManagedReference("contenido-progreso")
  @OneToMany(mappedBy = "contenido", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<ProgresoContenido> progresoContenidos = new HashSet<>();
}
