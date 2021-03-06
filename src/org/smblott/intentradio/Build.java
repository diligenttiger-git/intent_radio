package org.smblott.intentradio;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.text.SimpleDateFormat;

public class Build
{
   static private String build = null;

   // source: http://stackoverflow.com/questions/7607165/how-to-write-build-time-stamp-into-apk
   //
   public static String getBuildDate(Context context)
   {
      if ( build != null )
         return build;

      try
      {
         ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
         ZipFile file = new ZipFile(info.sourceDir);
         ZipEntry entry = file.getEntry("classes.dex");
         long time = entry.getTime();
         build = SimpleDateFormat.getInstance().format(new java.util.Date(time));
      }
      catch (Exception e)
         { build = "Unknown"; }

      return build;
   }
}
