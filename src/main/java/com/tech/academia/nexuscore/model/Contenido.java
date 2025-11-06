package com.tech.academia.nexuscore.model;

import com.tech.academia.nexuscore.model.enums.TipoContenido;
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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
  @Column(nullable = false)
  private TipoContenido tipo;

  @Column(name = "url_archivo", nullable = false)
  private String urlArchivo;

  @Column(name = "duracion_minutos", nullable = false)
  private Integer duracionMinutos;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modulo_id", nullable = false)
  private Modulo modulo;
}
