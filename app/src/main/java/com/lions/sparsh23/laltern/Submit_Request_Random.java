package com.lions.sparsh23.laltern;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aviadmini.quickimagepick.PickSource;
import com.aviadmini.quickimagepick.QuickImagePick;
import com.bumptech.glide.Glide;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

public class Submit_Request_Random extends AppCompatActivity {


    ImageView imageView, back;
    ImageLoader imageLoader;
    Button submit ;
    DBHelper dbHelper;
    EditText description, quantity, craftreq;
    String DOWN_URL = "http://www.whydoweplay.com/lalten/InsertReqDirect.php";
    SessionManager sessionManager;


    QuickImagePick.Callback callback = new QuickImagePick.Callback() {
        @Override
        public void onImagePicked(@NonNull PickSource pickSource, int i, @NonNull Uri uri) {



            Log.d("uri image", uri.toString());

            Glide.with(getApplicationContext())
                    .load(uri)
                    .fitCenter()
                    .into(imageView);

          // String urlimg = "file://"+uri.getPath();


            //imageLoader.displayImage(urlimg,imageView);
        }

        @Override
        public void onError(@NonNull PickSource pickSource, int i, @NonNull String s) {

        }

        @Override
        public void onCancel(@NonNull PickSource pickSource, int i) {

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit__request__random);


        sessionManager = new SessionManager(getApplicationContext());

        dbHelper = new DBHelper(getApplicationContext());

        description = (EditText)findViewById(R.id.desreq);
        quantity = (EditText)findViewById(R.id.requestquantity);
        craftreq = (EditText)findViewById(R.id.requestcraft);
        back    =   (ImageView)findViewById(R.id.back_submit_request);
        submit = (Button)findViewById(R.id.reqbutton);
        ImageLoaderConfiguration.Builder config1 = new ImageLoaderConfiguration.Builder(getApplicationContext());
        config1.threadPriority(Thread.NORM_PRIORITY - 2);
        config1.denyCacheImageMultipleSizesInMemory();
        config1.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config1.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config1.tasksProcessingOrder(QueueProcessingType.LIFO);
        config1.writeDebugLogs();
        imageLoader= ImageLoader.getInstance();
        imageLoader.init(config1.build());

        imageView = (ImageView)findViewById(R.id.reqproimg);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity.class);
                //intent.putExtra(com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity.INTENT_EXTRA_MODE, com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity.MODE_SINGLE);
                //intent.putExtra(com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity.INTENT_EXTRA_LIMIT, 1);
                //intent.putExtra(com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity.INTENT_EXTRA_SHOW_CAMERA, true);
                //startActivityForResult(intent, 67);
                RxImagePicker.with(getApplicationContext()).requestImage(Sources.GALLERY).subscribe(new Action1<Uri>() {
                    @Override
                    public void call(Uri uri) {
                        Log.d("path_chooser","file:///"+getRealPathFromURI_API19(getApplicationContext(),uri));
                        imageLoader.displayImage("file:///"+getRealPathFromURI_API19(getApplicationContext(),uri),imageView);
                        //Get image by uri using one of image loading libraries. I use Glide in sample app.
                    }
                });
            }
        });
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String uid=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                //dbHelper.InsertRequestData("ANY",sessionManager.getUserDetails().get("uid"),uid,description.getText().toString(),"https://www.whydoweplay.com/lalten/DirectRequestImages/"+uid+".jpeg","Under Review","WAIT",craftreq.getText().toString(),quantity.getText().toString());
                upload_data("any",sessionManager.getUserDetails().get("uid"),description.getText().toString(),craftreq.getText().toString(),quantity.getText().toString(),uid);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";
        if (uri.getHost().contains("com.android.providers.media")) {
            // Image pick from recent
            String wholeID = DocumentsContract.getDocumentId(uri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{id}, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
            return filePath;
        } else {
            // image pick from gallery
            return  getRealPathFromURI_API11to18(context,uri);
        }

    }

    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if(cursor != null){
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }



    @Override
    protected void onActivityResult(final int pRequestCode, final int pResultCode, final Intent pData) {

        if (!QuickImagePick.handleActivityResult(getApplicationContext(), pRequestCode, pResultCode, pData, this.callback)) {
            super.onActivityResult(pRequestCode, pResultCode, pData);
        }

    }

    public void upload_data(final String prouid, final String buyuid, final String des,  final String craft, final String quantity,final String uid)

    {
        final ProgressDialog loading = ProgressDialog.show(this,"Submitting request...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                       // String res = s.replaceAll("\\s+","");
                        //if (res.equals("Uploaded"))


                        if((s!=null)&s.equals("uploaded"))
                        {
                            loading.dismiss();

                            Toast.makeText(getApplicationContext(),s.toString(),Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            finish();

                            Log.d("submit req", ""+s.toString());

                        }
                        else
                        {

                            Toast.makeText(getApplicationContext(),"Error Occured, please try later",Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();


                        //Showing toast
                        Toast.makeText(Submit_Request_Random.this, "Error In Connectivity"+volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


               // GlideBitmapDrawable drawable = (GlideBitmapDrawable) imageView.getDrawable();

                BitmapDrawable drawable1 = (BitmapDrawable) imageView.getDrawable();
                //drawable1.getBitmap();
                Bitmap bitmap = drawable1.getBitmap();


                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("prouid",prouid);
                Keyvalue.put("buyuid",buyuid);
                Keyvalue.put("des",des);
                Keyvalue.put("requid",uid);
                Keyvalue.put("path",getStringImage(bitmap));
                Keyvalue.put("craft",craft);
                Keyvalue.put("quantity",quantity);

                return Keyvalue;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);


        return encodedImage;
    }





}
