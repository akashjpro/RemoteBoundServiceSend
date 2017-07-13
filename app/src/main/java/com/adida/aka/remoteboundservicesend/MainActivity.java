package com.adida.aka.remoteboundservicesend;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.adida.aka.common.ISimpleCalcu;

public class MainActivity extends AppCompatActivity {

    EditText mEdtNumA, mEdtNumB;
    private ISimpleCalcu mISimpleCalcu;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mISimpleCalcu = ISimpleCalcu.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        connectService();
    }

    private void connectService() {
        Intent intent = new Intent();
        intent.setClassName("com.adida.aka.remoteboundservicereceive", "com.adida.aka.remoteboundservicereceive.SumService");
        boolean isConnect = bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        if (isConnect){
            Toast.makeText(this, "Service Connected", Toast.LENGTH_SHORT).show();

        }
    }

    private void initView() {
        mEdtNumA = (EditText) findViewById(R.id.edt_num_a);
        mEdtNumB = (EditText) findViewById(R.id.edt_num_b);
    }


    public void sum(View view) {
        int a = Integer.parseInt(mEdtNumA.getText().toString());
        int b = Integer.parseInt(mEdtNumB.getText().toString());

        try {
            int result = mISimpleCalcu.add(a, b);
            Toast.makeText(this, "Sum: "+ result, Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
