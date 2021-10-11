package com.sh.migros.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


@Data
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Table(name = "TRACKING_TABLE")
public class Tracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name= "courierId")
    private Integer courierId;

    @Column(name = "storeId")
    private Integer storeId;

    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

}
