package org.smblott.intentradio;

import android.app.Activity;
import android.os.Bundle;

import android.content.Context;

import android.content.ClipData;
import android.content.ClipboardManager;

import android.view.View;
import android.widget.Button;

public class ClipButtons extends Activity
{

   private static String intent_play = null;
   private static String intent_stop = null;
   private static String intent_pause = null;
   private static String intent_restart = null;

   private static ClipboardManager clip_manager = null;

   @Override
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      Logger.init(getApplicationContext());

      intent_play = getString(R.string.intent_play);
      intent_stop = getString(R.string.intent_stop);
      intent_pause = getString(R.string.intent_pause);
      intent_restart = getString(R.string.intent_restart);

      clip_manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

      setContentView(R.layout.buttons);
   }

   /* ********************************************************************
    * Clip buttons...
    */

   public static void clip_play(View view)    { clip(intent_play); }
   public static void clip_stop(View view)    { clip(intent_stop); }
   public static void clip_pause(View view)   { clip(intent_pause); }
   public static void clip_restart(View view) { clip(intent_restart); }

   private static void clip(String text)
   {
      ClipData clip_data = ClipData.newPlainText("text", text);
      clip_manager.setPrimaryClip(clip_data);
      toast("Clipboard:\n" + text);
   }

   /* ********************************************************************
    * Utilities...
    */

   private static void toast(String msg)
      { Logger.toast(msg); }
}
