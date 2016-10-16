package ca.tutortutor.simpleapp;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

public class SimpleActivity extends Activity
{
   @Override
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState); // Always call superclass method first.
      System.out.println("onCreate(Bundle) called");
      Intent intent = new Intent();
      intent.setAction(Intent.ACTION_VIEW);
      intent.setType("image/jpeg");
      intent.addCategory(Intent.CATEGORY_DEFAULT);
      SimpleActivity.this.startActivity(intent);
   }
   @Override
   public void onDestroy()
   {
      super.onDestroy(); // Always call superclass method first.
      System.out.println("onDestroy() called");
   }
}
