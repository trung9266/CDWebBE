package com.example.cdwebbe.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "historywatch")
public class HistoryWatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
