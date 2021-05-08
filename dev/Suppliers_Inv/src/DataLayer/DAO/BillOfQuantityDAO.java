package DataLayer.DAO;

import BussinessLayer.Response;
import BussinessLayer.ResponseT;
import BussinessLayer.Supplier.BillOfQuantities;
import DataLayer.DTO.BillOfQuantityDTO;

import java.sql.*;

public class BillOfQuantityDAO extends DAO {


    public Response insert(Integer supplierID, Integer productID, Integer minQuantity, Integer percentDiscount) {

        String billOfQuantity = "INSERT INTO BillOfQuantity (supplierID, productID, minQuantity, percentDiscount) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConn().value;
             PreparedStatement pstmt = conn.prepareStatement(billOfQuantity);) {

            // inserting to employee table
            pstmt.setInt(1, supplierID);
            pstmt.setInt(2, productID);
            pstmt.setInt(3, minQuantity);
            pstmt.setInt(4,percentDiscount);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response insert(BillOfQuantityDTO boq){
        return insert(boq.getSupplierID(),boq.getProductID(), boq.getMinQuantity(), boq.getPercentDiscount());
    }


    //SELECT
    public ResponseT<BillOfQuantityDTO> get(Integer supplierID){
        String billSQL = String.format("SELECT* FROM BillOfQuantity WHERE supplierID = %s", supplierID);

        try(Connection conn = getConn().value;
            Statement billStmt = conn.createStatement();
            ResultSet billRs = billStmt.executeQuery(billSQL);){

            if(billRs.isClosed())
                return new ResponseT<>(null, String.format("supplierID %s not found", supplierID));
            BillOfQuantityDTO bill = new BillOfQuantityDTO(billRs.getInt("supplierID"),
                    billRs.getInt("productID"), billRs.getInt("minQuantity"),
                    billRs.getInt("percentDiscount"));

            return new ResponseT<>(bill);

        }catch(SQLException e){
            e.printStackTrace();
            return new ResponseT<>(null, e.getMessage());
        }

    }

    public Response delete(Integer supplierID) {
        String SQL = "DELETE FROM BillOfQuantity WHERE supplierID = ?";
        try {
            ResponseT<Connection> r = getConn();
            if(!r.ErrorOccured()) {
                PreparedStatement ps = r.value.prepareStatement(SQL);
                ps.setInt(1, supplierID);

                if(!ps.execute()) {
                    return new Response("cannot delete bill of quantity from db");
                }
            }
        }catch (SQLException e) {
            return new Response(e.getMessage());
        }
        return new ResponseT(supplierID);
    }


    public Response updateMinQuantity(Integer supplierID, Integer productID, Integer newMinQ) {
        String SQL = "UPDATE BillOfQuantity SET minQuantity = ? WHERE supplierID = ? AND productID = ?";
        try {
            ResponseT<Connection> r = getConn();
            if(!r.ErrorOccured()) {
                PreparedStatement ps = r.value.prepareStatement(SQL);
                ps.setInt(1, newMinQ);
                ps.setInt(2, supplierID);
                ps.setInt(3, productID);

                if(!ps.execute()) {
                    return new Response("cannot update minimum quantity of "+productID+
                            " in bill of quantity of "+supplierID+" " +" to db");
                }
            }
        }catch (SQLException e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response updatePercentDiscount(Integer supplierID, Integer productID, Integer newPercent) {
        String SQL = "UPDATE BillOfQuantity SET precentDiscount = ? WHERE supplierID = ? AND productID = ?";
        try {
            ResponseT<Connection> r = getConn();
            if(!r.ErrorOccured()) {
                PreparedStatement ps = r.value.prepareStatement(SQL);
                ps.setInt(1, newPercent);
                ps.setInt(2, supplierID);
                ps.setInt(3, productID);

                if(!ps.execute()) {
                    return new Response("cannot update discount of "+productID+
                            " in bill of quantity of "+supplierID+" " +" to db");
                }
            }
        }catch (SQLException e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }
}
