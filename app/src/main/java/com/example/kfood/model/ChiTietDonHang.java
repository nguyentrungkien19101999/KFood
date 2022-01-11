package com.example.kfood.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChiTietDonHang {

@Expose
private String id;
@Expose
private String madonhang;
@Expose
private String masanpham;
@Expose
private String tensanpham;
@Expose
private String giasanpham;
@Expose
private String soluongsanpham;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getMadonhang() {
return madonhang;
}

public void setMadonhang(String madonhang) {
this.madonhang = madonhang;
}

public String getMasanpham() {
return masanpham;
}

public void setMasanpham(String masanpham) {
this.masanpham = masanpham;
}

public String getTensanpham() {
return tensanpham;
}

public void setTensanpham(String tensanpham) {
this.tensanpham = tensanpham;
}

public String getGiasanpham() {
return giasanpham;
}

public void setGiasanpham(String giasanpham) {
this.giasanpham = giasanpham;
}

public String getSoluongsanpham() {
return soluongsanpham;
}

public void setSoluongsanpham(String soluongsanpham) {
this.soluongsanpham = soluongsanpham;
}

}