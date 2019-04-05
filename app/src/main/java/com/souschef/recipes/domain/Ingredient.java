package com.souschef.recipes.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String ingeredientName;

    private Double weight;

    private String unit;

}
