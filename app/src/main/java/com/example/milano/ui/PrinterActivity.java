package com.example.milano.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.example.milano.R;
import com.example.milano.model.Produto;
import com.example.milano.utils.DataUtils;
import com.example.milano.utils.PrinterCommands;
import com.example.milano.utils.UtilsText;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PrinterActivity extends AppCompatActivity implements Runnable {

    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;
    private UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private String TAG = "PrinterActivityLog";
    private boolean isPrinter = false;
    private OutputStream outputStream;
    private Thread mBlutoothConnectThread;
    private List<Produto> produtos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);
    }

    @Override
    protected void onResume() {
        super.onResume();


        try {
            if (getIntent().getSerializableExtra("produtos") != null) {
                produtos = (List<Produto>) getIntent().getSerializableExtra("produtos");
                if (produtos.size() > 0) {
                    for (Produto p : produtos) {
                        Log.e(TAG, "Produtos " + p.getNome());
                    }

                    isPrinter = !isPrinter;
                    if (isPrinter)
                        imprimir();
                } else {
                    Log.e(TAG, "Empty list");
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void imprimir() {

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else {
                try {
                    ListPairedDevices();
                    Intent connectIntent = new Intent(PrinterActivity.this, DeviceListActivity.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void ListPairedDevices() {
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter.getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                Log.e(TAG, "PairedDevices: " + mDevice.getName() + "\n" + mDevice.getAddress());
            }
        }
    }

    @Override
    public void onActivityResult(int mRequestCode, int mResultCode,
                                 Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        switch (mRequestCode) {

            case REQUEST_CONNECT_DEVICE:

                //when receive result code, execute printer
                if (mResultCode == Activity.RESULT_OK) {

                    Log.e(TAG, "RESULT_OK ");
                    Bundle mExtra = mDataIntent.getExtras();
                    String mDeviceAddress = mExtra.getString("DeviceAddress");

                    //calling device
                    mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(mDeviceAddress);
                    Log.e(TAG, "Coming incoming address " + mDeviceAddress);

                    mBlutoothConnectThread = new Thread(PrinterActivity.this);
                    mBlutoothConnectThread.start();
                }
                break;

            case REQUEST_ENABLE_BT:
                if (mResultCode == Activity.RESULT_OK) {
                    ListPairedDevices();
                    Intent connectIntent = new Intent(this, DeviceListActivity.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    Toast.makeText(PrinterActivity.this, "Message", Toast.LENGTH_SHORT).show();
                }
                break;

            case 10:
                break;
        }
    }

    @Override
    public void run() {

        try {
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothSocket.connect();
            mHandler.sendEmptyMessage(0);
        } catch (IOException eConnectException) {
            Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(mBluetoothSocket);
            return;
        }
    }


    private void closeSocket(BluetoothSocket mBluetoothSocket) {
        try {
            mBluetoothSocket.close();
            Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            finish();
            Log.d(TAG, "CouldNotCloseSocket");
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            printer();
        }
    };

    protected void printer() {

        try {

            outputStream = mBluetoothSocket.getOutputStream();
            byte[] printformat = new byte[]{0x1B, 0x21, 0x03};
            outputStream.write(printformat);


            printPhoto(R.drawable.logo_milano);
            printCustom("Comercial Milano Brasil", 3, 1);
            printCustom("Caixas Retiradas", 3, 1);
            printCustom("INPI", 3, 1);
            printCustom(DataUtils.Companion.getDataAtualHora(), 0, 1);

            for (Produto p : produtos) {
                printCustom(p.getNome(), 0, 1);
            }

            printCustom("", 0, 1);
            printCustom("___________________________", 0, 1);
            printCustom("Assinatura", 0, 1);
            printCustom("", 0, 1);
            printCustom("Total de caixas: "+produtos.size(), 3, 1);
            printCustom("", 0, 1);
            printCustom("www.transeeder.com.br", 0, 1);
            printCustom("", 0, 1);
            printCustom("", 0, 1);
            printNewLine();
            printNewLine();
            outputStream.flush();
            finish();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print custom
    private void printCustom(String msg, int size, int align) {
        //Print config "mode"
        byte[] cc = new byte[]{0x1B, 0x21, 0x03};  // 0- normal size text
        //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
        byte[] bb = new byte[]{0x1B, 0x21, 0x08};  // 1- only bold text
        byte[] bb2 = new byte[]{0x1B, 0x21, 0x20}; // 2- bold with medium text
        byte[] bb3 = new byte[]{0x1B, 0x21, 0x10}; // 3- bold with large text

        try {
            switch (size) {
                case 0:
                    outputStream.write(cc);
                    break;
                case 1:
                    outputStream.write(bb);
                    break;
                case 2:
                    outputStream.write(bb2);
                    break;
                case 3:
                    outputStream.write(bb3);
                    break;
            }

            switch (align) {
                case 0:
                    //left align
                    outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                    break;
                case 1:
                    //center align
                    outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                    break;
                case 2:
                    //right align
                    outputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
                    break;
            }
            outputStream.write(msg.getBytes());
            outputStream.write(PrinterCommands.LF);
            //outputStream.write(cc);
            //printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void printPhoto(int img) {

        try {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), img);
            if (bmp != null) {
                byte[] command = UtilsText.decodeBitmap(bmp);
                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                printText(command);
            } else {
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

    //print new line
    private void printNewLine() {
        try {
            outputStream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print byte[]
    private void printText(byte[] msg) {
        try {
            // Print normal text
            outputStream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
