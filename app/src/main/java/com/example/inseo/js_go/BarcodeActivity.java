package com.example.inseo.js_go;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class BarcodeActivity extends AppCompatActivity {
    ImageView barcode_img;
    TextView barcode_label;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        String append="02";
        if (JSPreference.getValue(JSPreference.isLogin, false)) {
            barcode_img = (ImageView) findViewById(R.id.barcode_img);
            barcode_label = (TextView) findViewById(R.id.barcode_label);
            Bitmap barcode = CreateBarcode(append);
            barcode_img.setImageBitmap(barcode);
            String id = JSPreference.getValue(JSPreference.id,"");
            barcode_label.setText(id+append);
        }
    }

    private Bitmap CreateBarcode(String append) {
        MultiFormatWriter writer = new MultiFormatWriter();
        String data = JSPreference.getValue(JSPreference.id,"12151404")+append;
        try {
            String encodedData = Uri.encode(data,"utf-8");
            BitMatrix matrix = writer.encode(data, BarcodeFormat.CODE_128,360,150);
            Bitmap imageBitmap = Bitmap.createBitmap(360,150, Bitmap.Config.ARGB_8888);
            for (int i = 0; i < 360; i++) {//width
                for (int j = 0; j < 150; j++) {//height
                    imageBitmap.setPixel(i, j, matrix.get(i, j) ? Color.BLACK: Color.WHITE);
                }
            }

            if (imageBitmap != null) {
                return imageBitmap;
            } else {

            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
