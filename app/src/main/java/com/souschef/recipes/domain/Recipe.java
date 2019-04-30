package com.souschef.recipes.domain;

import android.support.annotation.NonNull;

import java.io.Serializable;
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
public class Recipe implements Serializable {

    private String dishName;

    private List<Step> Steps;

    private int timeToProduce;

    private String image;

//    @Ignore
//    private List<Ingredient> ingredients = Collections.emptyList();
//
//    @Ignore
//    private List<Step> steps = Collections.emptyList();

}
