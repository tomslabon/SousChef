package com.souschef.recipes.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Step {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int stepNumber;

    private String description;

    @ForeignKey(entity = Recipe.class, parentColumns = "id", childColumns = "recipeId")
    private int recipeId;
}
