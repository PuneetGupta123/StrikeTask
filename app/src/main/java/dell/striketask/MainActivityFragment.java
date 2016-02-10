package dell.striketask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }
    final String TAG = "MainActivityFragment";
    final String ProgressDialogMessage = "Getting Your E-Mails";
    final String apiToHit = "https://api.myjson.com/bins/1xubp";
    Activity activity;
    ProgressDialog progressDialog;
    RelativeLayout relativeLayout;
    Boolean noInternetConnection=false;
    ArrayList<Email> emailArrayList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    EmailAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_main, container, false);
        activity = getActivity();
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout);
        emailArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        if(ConnectivityUtils.isNetworkEnabled(getContext()))
        {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(ProgressDialogMessage);
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            getEmailConversations();
        }
        else
        {
            Log.d(TAG," in else if");
            noInternetConnection=true;
//            try{
//                ((OnNoIntenetConnectionListener) activity).onNoInternetConnection();
//            }catch (ClassCastException cce){
//                Log.d(TAG,cce.toString());
//            }
        }
        return view;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"OnActivityCreated");
        if(noInternetConnection)
        {
            Snackbar.make(MainActivity.coordinatorLayout,"No Internet Connection",Snackbar.LENGTH_LONG).show();
        }
    }



    public interface OnNoIntenetConnectionListener{
        public void onNoInternetConnection();
    }

    public void getEmailConversations()
    {
        MyVolley.init(getContext());
        RequestQueue queue = MyVolley.getRequestQueue();
        StringRequest myReq = new StringRequest( Request.Method.GET, apiToHit
                , reqSuccessListener(), reqErrorListener()) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                return headers;
            }

            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(25000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(myReq);
    }

    private Response.Listener<String> reqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"in volley success");
                Log.d(TAG, "Response" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray emails = data.getJSONArray("emails");
                    for(int i=0;i<emails.length();i++)
                    {
                        JSONObject email = emails.getJSONObject(i);
                        Log.d(TAG,emails.get(i).toString());
                        String id = email.getString("id");
                        String name = email.getString("name");
                        String timeStamp = email.getString("timestamp");
                        String description = email.getString("desc");
                        emailArrayList.add(new Email(id,name,timeStamp,description));
                        //emailArrayList.add(new Email("1","Puneet","7484949","Hey there"));
                    }
                    layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new EmailAdapter(emailArrayList);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.cancel();
            }
        };
    }

    private Response.ErrorListener reqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"in volley error");
                Log.d(TAG, error.toString());
            }
        };
    }

}
