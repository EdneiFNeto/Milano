package com.example.milano.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.milano.R;
import com.example.milano.dapter.ImpressoraAdater;
import com.example.milano.model.Impressora;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DeviceListActivity extends Activity {

    protected static final String TAG = "DeviceListActivity";
    private BluetoothAdapter mBluetoothAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private List<Impressora> lista;

    @Override
    protected void onCreate(Bundle mSavedInstanceState) {
        super.onCreate(mSavedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.device_list);

        TextView mac_addess = findViewById(R.id.mac_addess);

        setTitle("Clique para imprimir comprovante");
        setResult(Activity.RESULT_CANCELED);
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

        ListView mPairedListView = (ListView) findViewById(R.id.paired_devices);
        //mPairedListView.setAdapter(mPairedDevicesArrayAdapter);


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter.getBondedDevices();

        if (mPairedDevices.size() > 0) {
            lista = new ArrayList<>();
            //findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            for (BluetoothDevice mDevice : mPairedDevices) {
                lista.add(new Impressora(mDevice.getName(), mDevice.getAddress()));
                mPairedDevicesArrayAdapter.add(mDevice.getName() + "\n" + mDevice.getAddress());
                mac_addess.setText(mDevice.getAddress());
                String address = mac_addess.getText().toString();

                mBluetoothAdapter.cancelDiscovery();
                Bundle mBundle = new Bundle();
                mBundle.putString("DeviceAddress", address);
                Intent mBackIntent = new Intent();
                mBackIntent.putExtras(mBundle);
                setResult(Activity.RESULT_OK, mBackIntent);

                Log.e(TAG, "Device_Address " + address);
                finish();
            }

            mPairedListView.setAdapter(new ImpressoraAdater(this, lista));
        } else {
            String mNoDevices = "None Paired";//getResources().getText(R.string.none_paired).toString();
            mPairedDevicesArrayAdapter.add(mNoDevices);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> mAdapterView, View mView, int mPosition, long mLong) {

            try {

                mBluetoothAdapter.cancelDiscovery();
                Impressora impressora = (Impressora) mAdapterView.getItemAtPosition(mPosition);
                Bundle mBundle = new Bundle();
                mBundle.putString("DeviceAddress", impressora.getAddress());
                Intent mBackIntent = new Intent();
                mBackIntent.putExtras(mBundle);
                setResult(Activity.RESULT_OK, mBackIntent);
                Log.e(TAG, "Device_Address " + impressora.getAddress());
                finish();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };
}
