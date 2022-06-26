package com.example.cdwebbe.model;

import com.example.cdwebbe.model.audit.BaseEntity;
import com.example.cdwebbe.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "historyitem")
public class HistoryItem extends DateAudit {
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "movie_id",referencedColumnName = "id")
    private Movie movie ;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "historyId",referencedColumnName = "id")
    private History history ;
}
