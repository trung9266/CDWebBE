package com.example.cdwebbe.model;

import com.example.cdwebbe.model.audit.BaseEntity;
import com.example.cdwebbe.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "history")
public class History extends DateAudit {
    @JsonIgnore
    @OneToMany(mappedBy = "history")
    private List<HistoryItem> historyItems;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userID", referencedColumnName = "id")
    private User user;
    public History(User user) {
        this.user = user;
    }
}
