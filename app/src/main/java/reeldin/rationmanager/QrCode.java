package reeldin.rationmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * Created by gopikm on 4/11/16.
 */

public class QrCode extends AppCompatActivity {

    ImageView qrCodeImageview;
    String QRcode;
    public final static int WIDTH=500;
    String rq,wq,sq,kq, card_no;
    SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_qr);
        sp=getSharedPreferences("login", Context.MODE_PRIVATE);
        qrCodeImageview=(ImageView) findViewById(R.id.img_qr_code_image);
        rq=sp.getString("riceqt","0");
        wq=sp.getString("wheatqt","0");
        sq=sp.getString("sugarqt","0");
        kq=sp.getString("keroseneqt","0");
        card_no = sp.getString("card_no","");
        Thread t =new Thread(new Runnable() {
            @Override
            public void run() {
                QRcode="{\"card_no\":\""+card_no+"\",\"Rice\":\""+rq+"\",\"Wheat\":\""+wq+"\",\"Sugar\":\""+sq+"\",\"Kerosene\":\""+kq+"\"}";
                try{
                    synchronized (this){
                        wait(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    Bitmap bitmap = null;
                                    bitmap = encodeAsBitmap(QRcode);
                                    qrCodeImageview.setImageBitmap(bitmap);
                                }catch (WriterException w){
                                    w.printStackTrace();
                                }
                            }
                        });
                    }
                }catch (InterruptedException i){
                    i.printStackTrace();
                }
            }
        });
        t.start();
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 500, 0, 0, w, h);
        return bitmap;
    }

}
