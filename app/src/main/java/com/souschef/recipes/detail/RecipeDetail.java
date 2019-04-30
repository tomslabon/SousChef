package com.souschef.recipes.detail;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.souschef.R;
import com.souschef.recipes.domain.Recipe;
import com.souschef.recipes.domain.Step;

import java.util.Locale;

import javax.annotation.Nullable;

public class RecipeDetail extends AppCompatActivity implements TextToSpeech.OnInitListener {

    TextView recipeName;
    RecyclerView steps;
    private StepsAdapter recipesAdapter;
    private TextToSpeech mTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipeName = findViewById(R.id.recipeName);
        steps = findViewById(R.id.steps_list);

        Intent goToDeatilsIntent = getIntent();

        steps.setLayoutManager(new LinearLayoutManager(this));
        steps.setHasFixedSize(true);
        mTTS = new TextToSpeech(this, this);
        
        if(goToDeatilsIntent.hasExtra(Intent.EXTRA_TEXT)) {
            final String path = goToDeatilsIntent.getStringExtra(Intent.EXTRA_TEXT);
            FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
            mFirestore.document(path).addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    Recipe recipe = documentSnapshot.toObject(Recipe.class);
                    recipeName.setText(recipe.getDishName());

                    final Query query = FirebaseFirestore.getInstance().document(path)
                            .collection("steps");
                    FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<Step>()
                            .setQuery(query, Step.class).setLifecycleOwner(RecipeDetail.this)
                            .build();
                    recipesAdapter = new StepsAdapter(options,RecipeDetail.this, mTTS);
                    
                    steps.setAdapter(recipesAdapter);
                }
            });
        }
    }

    private void saySomething(String text, int qmode) {
        if (qmode == 1)
            mTTS.speak(text, TextToSpeech.QUEUE_ADD, null);
        else
            mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            if (mTTS != null) {
                int result = mTTS.setLanguage(Locale.US);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(this, "TTS language is not supported", Toast.LENGTH_LONG).show();
                } else {
                    saySomething("TTS is ready", 0);
                }
            }
        } else {
            Toast.makeText(this, "TTS initialization failed", Toast.LENGTH_LONG).show();
        }
    }
}
