
package com.thiner.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.thiner.utils.MyLog;
import com.thiner.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class PostJSONTask extends AsyncTask<String, Void, String> {
    private final Context mContext;

    public PostJSONTask(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(String... paramns) {

        int trys = 10;
        String result = null;

        while (trys > 0) {
            trys--;

            try {

                MyLog.info("POST: " + Utils.getServerURL() + " - " + paramns[0]);

                result = Utils.sendPOST(Utils.getServerURL(), paramns[0]);

                if (result != null) {
                    break;
                }

            } catch (final IOException e) {
                MyLog.debug("Unable to retrieve web page. URL may be invalid: "
                        + Utils.getServerURL() + " - " + paramns[0]);
            }

            try {
                Thread.sleep(1000);
            } catch (final InterruptedException e) {
                MyLog.error(e.getMessage());
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(String msg) {
        try {
            ((PostJSONInterface) mContext).callbackPostJSON(new JSONObject(msg));
        } catch (JSONException e) {
            MyLog.error("Error when creating JSON on PostJSONTask", e);
        }

    }

    public interface PostJSONInterface {
        public void callbackPostJSON(JSONObject msg);
    }
}
