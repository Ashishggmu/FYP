package com.Ashish.foodnetwork.admin;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.Ashish.foodnetwork.Api.ApiClient;
import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.admin.addCategory.ListCategoryActivity;
import com.Ashish.foodnetwork.admin.addProduct.AddProductActivity;
import com.Ashish.foodnetwork.admin.products.ListProductsActivity;
import com.Ashish.foodnetwork.utils.Constants;
import com.Ashish.foodnetwork.utils.PermissionUtils;
import com.Ashish.foodnetwork.utils.SharePrefrenceUtils;
import com.Ashish.foodnetwork.utils.response.CategoryResponse;
import com.Ashish.foodnetwork.utils.response.Dash;
import com.Ashish.foodnetwork.utils.response.DashResponse;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {

    private static final int TAKE_PICTURE = 2;
    private static final int PICK_PICTURE = 1;
    
    TextView pendingOrders, totalOrders,ShippedOrders,TotalCategories,totalCustomers,totalProducts;
    LinearLayout addCategory, imageLayout,categoryList, productsLL;
    private Uri imageUri;
    String currentPhotoPath;
    ImageView selectedIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        pendingOrders=findViewById(R.id.pendingOrdersTV);
        TotalCategories=findViewById(R.id.totalCategoriesTV);
        totalCustomers = findViewById(R.id.totalCustomersTV);
        totalOrders = findViewById(R.id.totalOrdersTV);
        ShippedOrders = findViewById(R.id.shippedOrdersTV);
        totalProducts = findViewById(R.id.totalProductsTV);
        addCategory = findViewById(R.id.addCategory);
        categoryList = findViewById(R.id.categoryList);
        productsLL = findViewById(R.id.productsLL);
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddCategoryView();
            }
        });

        categoryList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),ListCategoryActivity.class);
                startActivity(intent);


            }
        });
        productsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), ListProductsActivity.class);
                startActivity(intent);
            }
        });
        getDash();
    }

    private void getDash() {
        Call<DashResponse> dashResponseCall= ApiClient.getApiServices().getDash(SharePrefrenceUtils.getStringPreference(getApplicationContext(), Constants.API_KEY));

        dashResponseCall.enqueue(new Callback<DashResponse>() {
            @Override
            public void onResponse(Call<DashResponse> call, Response<DashResponse> response) {
                if (response.isSuccessful()){
                    setDash(response.body().getDash());
                }
            }

            @Override
            public void onFailure(Call<DashResponse> call, Throwable t) {

            }
        });
    }

    private void setDash(Dash dash) {
        pendingOrders.setText(dash.getPendingOrders().toString());
        TotalCategories.setText(dash.getCategories().toString());
        totalCustomers.setText(dash.getCustomers().toString());
        totalOrders.setText(dash.getProcessingOrders().toString());
        ShippedOrders.setText(dash.getShippedOrders().toString());
        totalProducts.setText(dash.getProducts().toString());
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openAddCategoryView() {
        LayoutInflater factory= LayoutInflater.from(this);
        View DialogView = factory.inflate(R.layout.custom_dialog_add_category,null);
        Dialog main_dialog= new Dialog(this, R.style.Base_Theme_AppCompat_Dialog);
        main_dialog.setContentView(DialogView);
        main_dialog.show();
        EditText name = (EditText) main_dialog.findViewById(R.id.catNameET);
        EditText desc = (EditText) main_dialog.findViewById(R.id.catDescET);
        Button upload = (Button) main_dialog.findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().isEmpty()&&currentPhotoPath!=null){
                    uploadCategoty(new File(currentPhotoPath), name.getText().toString(), desc.getText().toString(),main_dialog);
                }else{
                    Toast.makeText(AdminActivity.this, "Please Check Image or name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView cameraIV= (ImageView) main_dialog.findViewById(R.id.cameraIV);
        ImageView galleryIv=(ImageView) main_dialog.findViewById(R.id.galleryIV);
        selectedIV=(ImageView) main_dialog.findViewById(R.id.selectedIV);
        imageLayout=(LinearLayout) main_dialog.findViewById(R.id.imageLayout);

        cameraIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = null;
                try{
                file= createImageFile();}
                catch (IOException e){
                    e.printStackTrace();
                }
                if (PermissionUtils.isCameraPermissionGranted(AdminActivity.this,"",1)){
                    Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (file != null){
                        Uri photoURI= FileProvider.getUriForFile(AdminActivity.this,"com.example.android.fileprovider",file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(intent, TAKE_PICTURE);
                    }
                }
            }
        });
        galleryIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PermissionUtils.isStoragePermissionGranted(AdminActivity.this, " " , PICK_PICTURE)){
                    Intent chooseFile= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(chooseFile,PICK_PICTURE);
                }
            }
        });
        main_dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== TAKE_PICTURE){
            if(resultCode== Activity.RESULT_OK){
                File f=new File(currentPhotoPath);
                Uri contentUri=Uri.fromFile(f);
                selectedIV.setImageURI(contentUri);
                setCategoryImage();
            }
        }else if (requestCode==PICK_PICTURE){
            if(resultCode==Activity.RESULT_OK){
                setCategoryImage();
                selectedIV.setImageURI(data.getData());
                String[] filePath= {MediaStore.Images.Media.DATA};
                Cursor c= getContentResolver().query(data.getData(), filePath,null,null, null);
                c.moveToFirst();
                int columnIndex= c.getColumnIndex(filePath[0]);
                String picturePath= c.getString(columnIndex);
                c.close();
                currentPhotoPath=picturePath;
            }
        }
    }

    private void setCategoryImage() {
        imageLayout.setVisibility(View.GONE);
        selectedIV.setVisibility(View.VISIBLE);
    }

    private File createImageFile() throws IOException {
        String timeStamp= new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileNAme = "JPEG_"+ timeStamp+"_";
        File storegeDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileNAme,
                ".jpg",
                storegeDir
        );

        currentPhotoPath=image.getAbsolutePath();
        return image;
    }

    private void uploadCategoty(File file, String name, String desc, Dialog dialog) {

        ProgressDialog progressDialog =ProgressDialog.show(this,"","Uploading... Please Wait...", false);
        RequestBody catName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody catDesc=RequestBody.create(MediaType.parse("text/plain"),desc);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(),RequestBody.create(MediaType.parse("image/*"),file));
        Call<CategoryResponse> responseCall= ApiClient.getApiServices().uploadCategory(SharePrefrenceUtils.getStringPreference(getApplicationContext(),Constants.API_KEY),filePart,catName,catDesc);
        responseCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    dialog.dismiss();
                    Toast.makeText(AdminActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.dismiss();

                    Toast.makeText(AdminActivity.this,"Upload Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

                dialog.dismiss();
                Toast.makeText(AdminActivity.this, "Upload Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void addProduct(View view){
        Intent intent= new Intent(this, AddProductActivity.class);
        startActivity(intent);
    }
}