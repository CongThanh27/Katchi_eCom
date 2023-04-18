package com.marwaeltayeb.souq.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Product implements Parcelable {

    @SerializedName("id")
    private int productId;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("price")
    private double productPrice;
    @SerializedName("quantity")
    private int productQuantity;
    @SerializedName("supplier")
    private String productSupplier;
    @SerializedName("category")
    private String productCategory;
    @SerializedName("image")
    private String productImage;
    @SerializedName("describe")
    private String describe;
    @SerializedName("trademark")
    private String trademark;
    @SerializedName("origin")
    private String origin;
    @SerializedName("sex")
    private String sex;
    @SerializedName("skinproblems")
    private String skinproblems;
    @SerializedName("active")
    private int active;
    @SerializedName("isFavourite")
    private int isFavourite;
    @SerializedName("isInCart")
    private int isInCart;
    // Include child Parcelable objects
    private Product mInfo;

    public Product(int productId, String productName, double productPrice, int productQuantity, String productSupplier, String productCategory, String productImage, String describe, String trademark, String origin, String sex, String skinproblems, int active) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productSupplier = productSupplier;
        this.productCategory = productCategory;
        this.productImage = productImage;
        this.describe = describe;
        this.trademark = trademark;
        this.origin = origin;
        this.sex = sex;
        this.skinproblems = skinproblems;
        this.active = active;
    }


    public Product() { }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductSupplier() {
        return productSupplier;
    }

    public void setProductSupplier(String productSupplier) {
        this.productSupplier = productSupplier;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSkinproblems() {
        return skinproblems;
    }

    public void setSkinproblems(String skinproblems) {
        this.skinproblems = skinproblems;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    public int isFavourite() {
        return isFavourite;
    }

    public int isInCart() {
        return isInCart;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite ? 1 : 0;
    }

    public void setIsInCart(boolean isInCart) {
        this.isInCart = isInCart ? 1 : 0;
    }

    // Write the values to be saved to the `Parcel`.
    @Override
    public void writeToParcel(Parcel out, int flags) {
        //được sử dụng để đóng gói các giá trị của đối tượng Product vào một Parcel
        out.writeInt(productId);
        out.writeString(productName);
        out.writeDouble(productPrice);
        out.writeInt(productQuantity);
        out.writeString(productSupplier);
        out.writeString(productCategory);
        out.writeString(productImage);
        out.writeString(describe);
        out.writeString(trademark);
        out.writeString(origin);
        out.writeString(sex);
        out.writeString(skinproblems);
        out.writeInt(active);
        out.writeInt(isFavourite);
        out.writeInt(isInCart);
        out.writeParcelable(mInfo, flags);
    }

    // Retrieve the values written into the `Parcel`.
    private Product(Parcel in) {
        //được sử dụng để tạo ra đối tượng Product từ Parcel
        productId = in.readInt();
        productName = in.readString();
        productPrice = in.readDouble();
        productQuantity = in.readInt();
        productSupplier = in.readString();
        productCategory = in.readString();
        describe= in.readString();
        trademark= in.readString();
        origin= in.readString();
        sex= in.readString();
        skinproblems= in.readString();
        active = in.readInt();
        productImage = in.readString();
        isFavourite = in.readInt();
        isInCart = in.readInt();
        mInfo = in.readParcelable(Product.class.getClassLoader());
    }
    @Override
    public int describeContents() {
        return 0;
    }
//cho phép đối tượng của lớp này có thể được truyền qua Intent hoặc được lưu trữ và khôi phục lại trạng thái của nó.
    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>() {

        // This simply calls our new constructor and
        // passes along `Parcel`, and then returns the new object!
        //Phương thức tạo createFromParcel(Parcel in) được gọi để tạo đối tượng Product từ Parcel được truyền vào.
        //Parcel ở đây là một đối tượng dùng để đóng gói dữ liệu của đối tượng Product
        //và gửi nó qua các thành phần khác của ứng dụng Android.
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }// trả về một mảng mới của đối tượng Product với kích thước được chỉ định.
    };
}
