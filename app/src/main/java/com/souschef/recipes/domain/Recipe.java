package com.souschef.recipes.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String dishName;

    @Ignore
    private List<Ingredient> ingredients = Collections.emptyList();

    @Ignore
    private List<Step> steps = Collections.emptyList();

}
