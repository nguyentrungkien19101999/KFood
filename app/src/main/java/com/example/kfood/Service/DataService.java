package com.example.kfood.Service;



import com.example.kfood.model.ChiTietDonHang;
import com.example.kfood.model.DonHang;
import com.example.kfood.model.Sanpham;
import com.example.kfood.model.ChiTietDonHang;
import com.example.kfood.model.DonHang;
import com.example.kfood.model.Sanpham;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataService {

    @Multipart
    @POST("themanhmonan.php")
    Call<String> ThemAnhMonAn(@Part MultipartBody.Part anh);

    @FormUrlEncoded
    @POST("insert.php")
    Call<String> InsertData(@Field("tensp") String ten
            , @Field("gia") String gia
            , @Field("hinhanh") String hinhanh
            , @Field("mota") String mota
            , @Field("idsp") String idsp);

    @GET("getallfood.php")
    Call<List<Sanpham>> GetAllFood();

    @FormUrlEncoded
    @POST("delete.php")
    Call<String> DeleteFood(@Field("id") String id, @Field("hinhanh") String hinh);

    @FormUrlEncoded
    @POST("update.php")
    Call<String> UpdateData(@Field("ten") String ten
                            , @Field("gia") String gia
                            ,@Field("mota") String mota
                            ,@Field("id") String id);

    @GET("getallcart.php")
    Call<List<DonHang>> getListCart();

    @FormUrlEncoded
    @POST("getchitietdonhang.php")
    Call<List<ChiTietDonHang>> getChitietdonhang(@Field("madonhang") String madonhang);
}
