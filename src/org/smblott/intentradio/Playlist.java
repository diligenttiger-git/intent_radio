package org.smblott.intentradio;

import java.util.Random;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import android.text.TextUtils;
import java.util.List;
import android.os.AsyncTask;

public abstract class Playlist extends AsyncTask<String, Void, String>
{
   private IntentPlayer player;
   private int then;

   Playlist(IntentPlayer the_player)
   {
      super();
      player = the_player;
      then = Counter.now();
      log("Playlist: then=" + then);
   }

   abstract String filter(String line);

   /* ********************************************************************
    * Asynchronous task to fetch playlist...
    */

   protected String doInBackground(String... args)
   {
      String url = fetch_url(args[0]);

      if ( url == null )
      {
         log("Playlist: failed to extract URL from playlist.");
         return null;
      }

      if ( url.endsWith(PlaylistPls.suffix) || url.endsWith(PlaylistM3u.suffix) )
      {
         log("Playlist: another playlist!");
         return null;
      }

      return url;
   }

   // This runs on the main thread...
   //
   protected void onPostExecute(String url) {
      if ( url != null && ! isCancelled() )
         player.play(url, then);
   }

   public boolean finished()
      { return getStatus() == AsyncTask.Status.FINISHED; }

   /* ********************************************************************
    * Fetch a single (random) url from a playlist...
    */

   private static Random random = null;

   private String fetch_url(String url)
   {
      List<String> lines = HttpGetter.httpGet(url);

      for (int i=0; i<lines.size(); i+= 1)
         log("Playlist lines: ", lines.get(i));

      for (int i=0; i<lines.size(); i+= 1)
         lines.set(i, filter(lines.get(i).trim()));

      for (int i=0; i<lines.size(); i+= 1)
         if ( lines.get(i).length() != 0 )
            log("Playlist filtered: ", lines.get(i));

      List<String> links = get_links(TextUtils.join("\n", lines));
      if ( links.size() == 0 )
         return null;

      for (int i=0; i<links.size(); i+= 1)
         log("Playlist links: ", links.get(i));

      if ( random == null )
         random = new Random();

      return links.get(random.nextInt(links.size()));
   }

   /* ********************************************************************
    * Extract list of urls from string...
    *
    * source: http://blog.houen.net/java-get-url-from-string/
    */

   private static final String url_regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
   private static Pattern url_pattern = null;

   private static List<String> get_links(String text)
   {
      ArrayList links = new ArrayList<String>();

      if ( url_pattern == null )
         url_pattern = Pattern.compile(url_regex);

      Matcher matcher = url_pattern.matcher(text);

      while( matcher.find() )
      {
         String str = matcher.group();
         if (str.startsWith("(") && str.endsWith(")"))
            str = str.substring(1, str.length() - 1);
         links.add(str);
      }

      return links;
   }

   /* ********************************************************************
    * Logging...
    */

   private static void log(String... msg)
      { Logger.log(msg); }
}
