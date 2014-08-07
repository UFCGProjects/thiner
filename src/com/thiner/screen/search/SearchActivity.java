
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.thiner.R;
import com.thiner.asynctask.GetJSONTask;
import com.thiner.asynctask.GetJSONTask.GetJSONInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchActivity extends Activity implements GetJSONInterface {

    Button btGoSearch;
    EditText searchText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Get Refferences of Views

        // edConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);

        btGoSearch = (Button) findViewById(R.id.buttonCreateAccount);
        btGoSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                sendGetUserInformation();

            }

        });
    }

    private void sendGetUserInformation() {
        searchText = (EditText) findViewById(R.id.TextViewSearch);

        final String username = "username=" + searchText.getText().toString();
        new GetJSONTask(this).execute(username);
    }

    @Override
    public void callbackDownloadJSON(final JSONObject json) {

        final TextView emptySearch;
        final ListView listSearch;

        try {
            final JSONArray users = json.getJSONArray("users");
            if (users.length() == 0) {
                emptySearch = (TextView) findViewById(R.id.empty);
                emptySearch.setVisibility(View.VISIBLE);
            } else {
                listSearch = (ListView) findViewById(R.id.list);
            }
        } catch (final JSONException e) {
            e.printStackTrace();
        }

    }
}
