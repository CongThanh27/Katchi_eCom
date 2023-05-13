package com.marwaeltayeb.souq.view;

import static com.marwaeltayeb.souq.storage.LanguageUtils.loadLocale;
import static com.marwaeltayeb.souq.utils.Constant.PRODUCTID;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.adapter.ThongTinDonHangAdapter;
import com.marwaeltayeb.souq.databinding.ActivityCartBinding;
import com.marwaeltayeb.souq.model.Ordering;
import com.marwaeltayeb.souq.model.ProductApiResponse;
import com.marwaeltayeb.souq.model.ProductInCart;
import com.marwaeltayeb.souq.model.Shipping;
import com.marwaeltayeb.souq.model.User;
import com.marwaeltayeb.souq.net.APIService;
import com.marwaeltayeb.souq.net.RetrofitClient;
import com.marwaeltayeb.souq.storage.LoginUtils;
import com.marwaeltayeb.souq.viewmodel.OrderingViewModel;
import com.marwaeltayeb.souq.viewmodel.ShippingViewModel;

import java.io.IOException;
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
    private ShippingViewModel shippingViewModel;
    private OrderingViewModel orderingViewModel;


    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dathang);
        shippingViewModel = ViewModelProviders.of(this).get(ShippingViewModel.class);
        orderingViewModel = ViewModelProviders.of(this).get(OrderingViewModel.class);
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
        int userId = user.getId();
        tienhanhdathang = findViewById(R.id.button);
        String address = user.getAddress();
        String phone =  user.getPhone_number();
        String city = user.getAddress();
        String country = user.getAddress();
        String zip = "123";
        List<ProductInCart> cartList = this.cartList;
        tienhanhdathang.setOnClickListener(v -> {
            //vòng for qua từng sản phẩm trong giỏ hàng
            //random từ 100000 đến 999999
            int random = (int) (Math.random() * (999999 - 100000)) + 100000;
            for (int i = 0; i < cartList.size(); i++) {
                int productID = cartList.get(i).getProductId();
                int quantity = cartList.get(i).getCartquantity();
                Shipping shipping = new Shipping(address, city, country, zip, phone,userId, productID);
                shippingViewModel.addShippingAddress(shipping).observe(this, responseBody -> {
                    try {
                        Toast.makeText(this, responseBody.string()+"", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    orderProduct(user,productID,quantity,random);
                });
            }
        });
    }
    private void orderProduct( User user,int productID,int quantity, int random) {
        String nameOnCard = user.getName();
        String cardNumber = user.getPhone_number();
        String year = "2023";
        String month = "12";
        String fullDate = year + "-" + month + "-00";
        Ordering ordering = new Ordering(nameOnCard,cardNumber,fullDate,user.getId(),productID,quantity,random);
        orderingViewModel.orderProduct(ordering).observe(this, responseBody -> {
            try {
                Toast.makeText(this, responseBody.string() + "", Toast.LENGTH_SHORT).show();
                finish();
                Intent homeIntent = new Intent(this, OrderPlacedActivity.class);
                homeIntent.putExtra("maDonHang", random);
                startActivity(homeIntent);
            } catch (IOException e) {
                e.printStackTrace();
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
