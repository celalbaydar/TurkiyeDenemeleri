package com.turkiyedenemeleri.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.turkiyedenemeleri.R;
import com.turkiyedenemeleri.util.YGSCalculate;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class SinavSonucuFragment extends DialogFragment {
    ProgressDialog dialog;


    public SinavSonucuFragment() {
    }


    public static SinavSonucuFragment newInstance(double turkceNet, double sosyalNet, double matNet, double fenNet) {
        SinavSonucuFragment fragment = new SinavSonucuFragment();
        Bundle args = new Bundle();
        args.putDouble("turkceNet", turkceNet);
        args.putDouble("sosyalNet", sosyalNet);
        args.putDouble("matNet", matNet);
        args.putDouble("fenNet", fenNet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private String html;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_sinav_sonucu, container, false);
        ButterKnife.bind(this, view);


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        Bundle bundle = getArguments();
        double turkce = bundle.getDouble("turkceNet");
        double sosyal = bundle.getDouble("sosyalNet");
        double mat = bundle.getDouble("matNet");
        double fen = bundle.getDouble("fenNet");
        YGSCalculate calculate = new YGSCalculate(turkce,sosyal,mat,fen);


        params.put("ygs11h", calculate.getYgs1());
        params.put("ygs21h", calculate.getYgs2());
        params.put("ygs31h", calculate.getYgs3());
        params.put("ygs41h", calculate.getYgs4());
        params.put("ygs51h", calculate.getYgs5());
        params.put("ygs61h", calculate.getYgs6());


        client.post("http://puanza.com/arama.php", params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                String s2 = new String(response);

                WebView webview = (WebView) view.findViewById(R.id.webView);
                webview.getSettings().setJavaScriptEnabled(true);
                webview.loadDataWithBaseURL("", s2, "text/html", "UTF-8", "");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }


}