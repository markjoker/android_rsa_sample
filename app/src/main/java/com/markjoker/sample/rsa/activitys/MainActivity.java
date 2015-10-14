package com.markjoker.sample.rsa.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.markjoker.sample.rsa.R;
import com.markjoker.sample.rsa.utils.Base64Utils;
import com.markjoker.sample.rsa.utils.RsaUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText mBeforeEncodeView;
    private EditText mAfterEncodeView;
    private EditText mAfterDecodeView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    
    private void init()
    {
        findViewById(R.id.btn_decode).setOnClickListener(this);
        findViewById(R.id.btn_encode).setOnClickListener(this);
        mBeforeEncodeView = (EditText)findViewById(R.id.et_before_encode);
        mAfterEncodeView = (EditText)findViewById(R.id.et_after_encode);
        mAfterDecodeView = (EditText)findViewById(R.id.et_after_decode);
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_encode:
                doEncode();
                break;
            case R.id.btn_decode:
                doDecode();
                break;
            default:
                break;
        }
    }
    
    private void doDecode()
    {
        String encodeStr = mAfterEncodeView.getText().toString().trim();
        try
        {
            InputStream privateIn = getResources().getAssets().open("rsa_private_key.pem");
            PrivateKey privateKey = RsaUtils.loadPrivateKey(privateIn);
            byte[] base64Decode = Base64Utils.decode(encodeStr);
            byte[] decrypt = RsaUtils.decryptData(base64Decode, privateKey);
            String decodeStr = new String(decrypt);
            mAfterDecodeView.setText(decodeStr);
        }
        
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void doEncode()
    {
        String source = mBeforeEncodeView.getText().toString().trim();
        try
        {
            InputStream in = getResources().getAssets().open("rsa_public_key.pem");
            PublicKey publicKey = RsaUtils.loadPublicKey(in);
            byte[] encrypt = RsaUtils.encryptData(source.getBytes(), publicKey);
            String afterEncode = Base64Utils.encode(encrypt);
            mAfterEncodeView.setText(afterEncode);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
}
