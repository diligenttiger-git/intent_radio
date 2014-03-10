package org.smblott.intentradio;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;

import android.content.Context;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IntentRadio extends Activity
{
   @Override
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      Context context = getApplicationContext();
      Logger.init(context);

      setContentView(R.layout.main);

      String version = getString(R.string.version);
      version = "<p>Version: " + version + "<br>\n";

      String build_date = Build.getBuildDate(context);
      build_date = "Build: " + build_date + "\n</p>\n";

      String file = ReadRawTextFile.read(getApplicationContext(),R.raw.message);
      Spanned html = Html.fromHtml(file + version + build_date );

      TextView text = (TextView) findViewById(R.id.text);
      text.setMovementMethod(LinkMovementMethod.getInstance());
      text.setText(html);
   }

   /* ********************************************************************
    * Clip buttons...
    */

   public void clip_buttons(View v)
   {
      Intent c = new Intent(IntentRadio.this, ClipButtons.class);
      startActivity(c);
   }
}
