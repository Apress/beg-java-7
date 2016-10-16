package ca.tutortutor.simpleapp;

import android.app.Activity;

import android.os.Bundle;

public class SimpleActivity extends Activity
{
   @Override
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState); // Always call superclass method first.
      System.out.println("onCreate(Bundle) called");
   }
   @Override
   public void onDestroy()
   {
      super.onDestroy(); // Always call superclass method first.
      System.out.println("onDestroy() called");
   }
}
