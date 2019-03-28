package ro.pub.cs.systems.eim.practicaltest01var01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static ro.pub.cs.systems.eim.practicaltest01var01.Constants.CANCEL;
import static ro.pub.cs.systems.eim.practicaltest01var01.Constants.CARDINAL_POINTS;
import static ro.pub.cs.systems.eim.practicaltest01var01.Constants.REGISTER;

public class PracticalTest01Var01SecondaryActivity extends AppCompatActivity {

    Button registerButton;
    Button cancelButton;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var01_secondary);

        registerButton = findViewById(R.id.register_btn);
        cancelButton = findViewById(R.id.cancel_btn);

        resultTextView = findViewById(R.id.result_tv);

        ButtonClickListener listener = new ButtonClickListener();
        registerButton.setOnClickListener(listener);
        cancelButton.setOnClickListener(listener);

        Intent intent = getIntent();
        if (intent != null) {
            resultTextView.setText(intent.getStringExtra(CARDINAL_POINTS));

        } else {
            resultTextView.setText("0");
        }
    }

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.register_btn: {
                    setResult(REGISTER);
                    break;
                }
                case R.id.cancel_btn:
                    setResult(CANCEL);
                    break;

            }
            finish();
        }
    }
}
