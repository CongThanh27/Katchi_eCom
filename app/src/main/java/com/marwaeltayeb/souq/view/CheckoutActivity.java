package com.marwaeltayeb.souq.view;

import static com.marwaeltayeb.souq.storage.LanguageUtils.loadLocale;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.adapter.ThongTinDonHangAdapter;
import com.marwaeltayeb.souq.databinding.ActivityCartBinding;
import com.marwaeltayeb.souq.model.ProductApiResponse;
import com.marwaeltayeb.souq.model.ProductInCart;
import com.marwaeltayeb.souq.model.User;
import com.marwaeltayeb.souq.net.APIService;
import com.marwaeltayeb.souq.net.RetrofitClient;
import com.marwaeltayeb.souq.storage.LoginUtils;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {
    private List<ProductInCart> cartList;
    RecyclerView recyclerView;
    APIService apiService;
    TextView nameuser, diachi, sdt, tongtien, tamtinh, phivanchuyen, giamgia;
    Button tienhanhdathang;
    private ThongTinDonHangAdapter thongTinDonHangAdapter;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dathang);
        //nhận thông tin từ CartAtivity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            cartList = (List<ProductInCart>) bundle.getSerializable("cartList");
        }
        //Thông tin đơn hàng
        recyclerView = findViewById(R.id.listOfInfo);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,false);
       // chỉnh độ rộng layoutManager bằng cha
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        thongTinDonHangAdapter = new ThongTinDonHangAdapter(cartList, this);
        recyclerView.setAdapter(thongTinDonHangAdapter);
        // thông tin người nhận
        nameuser= findViewById(R.id.txtnameuser);
        diachi = findViewById(R.id.txtđiachi);
        sdt = findViewById(R.id.txtsdt);
        //nameuser.setText(cartList.get(0).getUsername());
        User user = LoginUtils.getInstance(this).getUserInfo();
        nameuser.setText(user.getName());
        diachi.setText(user.getAddress());
        sdt.setText(user.getPhone_number());
        //Toast.makeText(this, user.getAddress(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, user.getPhone_number(), Toast.LENGTH_SHORT).show();
        //getUserInfo1(userID);
        //tổng tiền
        tamtinh = findViewById(R.id.txttamtinh);
        tongtien = findViewById(R.id.txtthanhtien);
        phivanchuyen = findViewById(R.id.txtphivanchuyen);
        giamgia = findViewById(R.id.txtgiamgia);
        //vòng for tính tổng tiền
        int tong = 0;
        for (int i = 0; i < cartList.size(); i++) {
            tong += cartList.get(i).getProductPrice() * cartList.get(i).getCartquantity();
        }
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(tong);
        tamtinh.setText((formattedPrice) + "đ");
        tongtien.setText((formattedPrice) + "đ");
        giamgia.setText("-0đ");
        TienHanhDatHang();
    }
    private void TienHanhDatHang() {
        User user = LoginUtils.getInstance(this).getUserInfo();
        tienhanhdathang = findViewById(R.id.button);
        String address = user.getAddress();
        String phone =  user.getPhone_number();
        List<ProductInCart> cartList = this.cartList;
        tienhanhdathang.setOnClickListener(v -> {
            //vòng for qua từng sản phẩm trong giỏ hàng
            for (int i = 0; i < cartList.size(); i++) {
                //int productID = cartList.get(i).getProductID();
                int quantity = cartList.get(i).getCartquantity();

            }
        });
    }
    private void getUserInfo(int userID) {
        RetrofitClient.getInstance()
                .getApi().getUserInFo(userID)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        // Log.v("getUserInfo", "Succeeded "+response.body().getName() );
                        if (response.body() == null) {
                            Log.v("getUserInfo", "Fail ");
                            return;
                        } else {
                            Log.v("getUserInfo", "Succeeded " + response.body().getName());
                            User user = response.body();
                            nameuser.setText(user.getName());
                            diachi.setText(user.getAddress());
                            sdt.setText(user.getPhone_number());
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                    }
                });
    }

    private void getUserInfo1(int userID) {
        apiService = RetrofitClient.getRetrofitUser().create(APIService.class);
        apiService.getUserInFo(userID).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Log.v("getUserInfo", "Succeeded " + response.body().getName());
                    User user = response.body();
                    nameuser.setText(user.getName());
                    diachi.setText(user.getAddress());
                    sdt.setText(user.getPhone_number());
                } else {
                    Log.v("getUserInfo", "Fail ");
                    int statusCode = response.code();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


    }
}
