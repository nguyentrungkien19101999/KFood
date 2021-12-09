package com.example.kfood.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DonHang implements Serializable {

@SerializedName("Id")
@Expose
private String id;
@SerializedName("Tenkhachhang")
@Expose
private String tenkhachhang;
@SerializedName("Sdt")
@Expose
private String sdt;
@SerializedName("Email")
@Expose
private String email;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getTenkhachhang() {
return tenkhachhang;
}

public void setTenkhachhang(String tenkhachhang) {
this.tenkhachhang = tenkhachhang;
}

public String getSdt() {
return sdt;
}

public void setSdt(String sdt) {
this.sdt = sdt;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

}