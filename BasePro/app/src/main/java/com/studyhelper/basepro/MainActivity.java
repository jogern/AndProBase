package com.studyhelper.basepro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.studyhelper.baselib.ui.BaseActivity;
import com.studyhelper.basepro.test.TestAty;

public class MainActivity extends BaseActivity {

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
      }

      public void onTestClick(View view) {
            startActivity(new Intent(this,TestAty.class));
      }
}
