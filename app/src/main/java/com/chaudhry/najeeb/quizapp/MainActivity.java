package com.chaudhry.najeeb.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;


public class MainActivity extends AppCompatActivity {

    int resultRadioButton = 0;
    int resultCheckBoxRed = 0;
    int resultCheckBoxBlue = 0;
    int resultCheckBoxViolet = 0;
    int resultSpinner = 0;
    int resultToggleButton = 0;
    int resultEditText = 0;

    private RadioGroup mRadioGroup;
    private CheckBox mCheckBoxRed;
    private CheckBox mCheckBoxBlue;
    private CheckBox mCheckBoxViolet;
    private Spinner mColorSpinner;
    private ToggleButton mToggleButton;
    private EditText mEditText;

    // Implement anonymously interface CompoundButton.OnCheckedChangeListener to use on checkboxes
    private CompoundButton.OnCheckedChangeListener mCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (buttonView == mCheckBoxRed) resultCheckBoxRed = 1;
                if (buttonView == mCheckBoxBlue) resultCheckBoxBlue = 1;
                if (buttonView == mCheckBoxViolet) resultCheckBoxViolet = 1;
            }
            else {
                if (buttonView == mCheckBoxRed) resultCheckBoxRed = 0;
                if (buttonView == mCheckBoxBlue) resultCheckBoxBlue = 0;
                if (buttonView == mCheckBoxViolet) resultCheckBoxViolet = 0;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mCheckBoxRed = (CheckBox) findViewById(R.id.checkbox_red);
        mCheckBoxBlue = (CheckBox) findViewById(R.id.checkbox_blue);
        mCheckBoxViolet = (CheckBox) findViewById(R.id.checkbox_violet);
        mColorSpinner = (Spinner) findViewById(R.id.spinner_color);
        mToggleButton = (ToggleButton) findViewById(R.id.toggle_button);
        mEditText = (EditText) findViewById(R.id.edit_text_field);

        // Calling methods
        setUpRadioGroup();
        setUpCheckBox();
        setUpSpinner();
        setUpToggleButton();
    }


    private void setUpRadioGroup() {
        // Set up interface: RadioGroup.OnCheckedChangeListener
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            // Called when the checked radio button has changed. When the selection is cleared, checkedId is -1
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);
                if (checkedId == R.id.radio_button_1) resultRadioButton = 1;
                else resultRadioButton = 0;

            }
        });
    }


    private void setUpCheckBox() {
        // Set up interface: CompoundButton.OnCheckedChangeListener
        // Once user checks a checkbox, resultCheckBox... value will become 1
        mCheckBoxRed.setOnCheckedChangeListener(mCheckedChangeListener);
        mCheckBoxBlue.setOnCheckedChangeListener(mCheckedChangeListener);
        mCheckBoxViolet.setOnCheckedChangeListener(mCheckedChangeListener);
    }


    private void setUpSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter mySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_color_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        mySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mColorSpinner.setAdapter(mySpinnerAdapter);

        // Set the integer mSelected to the constant values
        mColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.spinner_pink))) resultSpinner = 1;
                    else resultSpinner = 0;
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ;  // do nothing
            }
        });
    }


    private void setUpToggleButton() {
        // Set up interface: CompoundButton.OnCheckedChangeListener
        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) resultToggleButton = 1;
                else resultToggleButton = 0;
            }
        });
    }


    public void submitButtonPressed(View view) {
        int resultCheckBox = 0; // Each time submit button clicked, resultCheckBox value starts with 0
        // Figure out Checkbox, only if red and violet checkboxes are clicked and blue checkbox is not clicked
        if ((resultCheckBoxRed==1) && (resultCheckBoxViolet==1) && (resultCheckBoxBlue==0)) resultCheckBox = 1;

        //Read EditText field
        if (mEditText.getText().toString().equalsIgnoreCase("yes")) resultEditText = 1;
        else resultEditText = 0;

        int result = 0;  // Each time submit button clicked, result value starts with 0
        result = resultRadioButton + resultCheckBox + resultSpinner + resultToggleButton + resultEditText;

        // Display result in Toast
        Toast.makeText(this, result + " out of 5 questions correct", Toast.LENGTH_LONG).show();
    }
}
