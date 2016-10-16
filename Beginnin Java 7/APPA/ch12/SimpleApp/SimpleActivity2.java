package ca.tutortutor.simpleapp;

import android.app.Activity;

import android.os.Bundle;

import android.widget.Toast;

public class SimpleActivity2 extends Activity
{
   @Override
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      Toast.makeText(this, getIntent().toString(), Toast.LENGTH_LONG).show();
   }
}
