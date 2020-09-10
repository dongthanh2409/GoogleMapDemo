package com.example.googlemapdemo.dao;

import com.example.googlemapdemo.helper.DBHelper;
import com.example.googlemapdemo.model.Apartment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApartmentDAO {

    public List<Apartment> findByPrice(double min, double max) throws  Exception {
        String sql = "SELECT * FROM Apartment WHERE price >= ? AND price <= ?";
        try(
                Connection con = new DBHelper().openConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ) {
            pstmt.setDouble(1, min);
            try (ResultSet rs = pstmt.executeQuery()) {
                List<Apartment> list = new ArrayList<>();
                while (rs.next()) {
                    Apartment apt = CreateApartment(rs);
                    list.add(apt);
                }
                return list;
            }
        }
    }

    public List<Apartment> findAll() throws  Exception {
        String sql = "SELECT * FROM Apartment";
        try(
                Connection con = new DBHelper().openConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    List<Apartment> list = new ArrayList<>();
                    while (rs.next()) {
                        Apartment apt = CreateApartment(rs);
                        list.add(apt);
                    }
                    return list;
            }

        }
    }

    private Apartment CreateApartment(ResultSet rs) throws SQLException {
        Apartment apt = new Apartment();
        apt.setAddress(rs.getString("address"));
        apt.setOwner(rs.getString("owner"));
        apt.setPrice(rs.getDouble("price"));
        apt.setLatitude(rs.getDouble("latitude"));
        apt.setLongtitue(rs.getDouble("longtitude"));
        apt.setPhone(rs.getString("phone"));
        return apt;
    }
}
