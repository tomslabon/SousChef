package com.souschef.recipes.detail;

import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.souschef.R;
import com.souschef.recipes.domain.Step;

public class StepsAdapter extends FirestoreRecyclerAdapter<Step, StepsAdapter.StepViewHolder> {

    private static final String TAG = StepsAdapter.class.getSimpleName();

    RecipeDetail redipeDetailsActivity;

    TextToSpeech mTTS;

    public StepsAdapter(@NonNull FirestoreRecyclerOptions options, RecipeDetail recipesListActivity, TextToSpeech mTTS) {
        super(options);
        this.mTTS = mTTS;
        this.redipeDetailsActivity = recipesListActivity;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new StepsAdapter.StepViewHolder(inflater.inflate(R.layout.activity_step_detail, viewGroup, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull StepViewHolder holder, int position, @NonNull Step model) {
        holder.bind(model, position);
        Log.d(TAG, "onBindViewHolder");
    }

    public class StepViewHolder extends RecyclerView.ViewHolder {
        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(Step step, int position) {
            Log.d(TAG, "Bind new recipe");
            if (step != null) {
                TextView stepNumber = itemView.findViewById(R.id.step_id);
                TextView stepDescription = itemView.findViewById(R.id.step_description);

                stepNumber.setText(String.valueOf(step.getNumber()));
                stepDescription.setText(step.getDescription());
                mTTS.speak(step.getDescription(), TextToSpeech.QUEUE_FLUSH, null);
//                updatedAtView = itemView.findViewById(R.id.taskUpdatedAt);
//                priorityView = itemView.findViewById(R.id.priorityTextView);            }
            }
        }
    }
}
