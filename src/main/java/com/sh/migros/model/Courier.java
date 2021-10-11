package com.sh.migros.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Data
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "COURIERS")
public class Courier {
    @Id
    @Column(name = "id")
    private Integer courierId;

    @Column(name = "LATITUDE")
    private double lat;

    @Column(name = "LONGITUDE")
    private double lng;

    @Column(name = "DISTANCE")
    private double totalTravelDistance = 0.0;






}
