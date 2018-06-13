package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView description;
    TextView origin;
    TextView ingredients;
    TextView also_known;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        description = (TextView) findViewById(R.id.description_tv);
        origin = (TextView) findViewById(R.id.origin_tv);
        ingredients = (TextView) findViewById(R.id.ingredients_tv);
        also_known = (TextView) findViewById(R.id.also_known_tv);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        description.setText(sandwich.getDescription());
        origin.setText(sandwich.getPlaceOfOrigin());
        StringBuffer sBuf = new StringBuffer();
        sBuf.append("");
        //read contents of the ingredients list
        if(sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0){
            for (String item : sandwich.getIngredients()) {
                sBuf.append(item);
                sBuf.append(",");
            }
            sBuf.deleteCharAt(sBuf.lastIndexOf(","));
        }
        ingredients.setText(sBuf.toString());
        sBuf = null;
        //read contents of also known as List
        sBuf = new StringBuffer();
        sBuf.append("");
        if(sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0){
            for (String item : sandwich.getAlsoKnownAs()) {
                sBuf.append(item);
                sBuf.append(",");
            }
            sBuf.deleteCharAt(sBuf.lastIndexOf(","));
        }
        also_known.setText(sBuf.toString());
    }
}
