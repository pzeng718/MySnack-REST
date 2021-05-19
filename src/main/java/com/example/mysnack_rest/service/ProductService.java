package com.example.mysnack_rest.service;

import com.example.mysnack_rest.db.DatabaseConnector;
import com.example.mysnack_rest.db.DatabaseUtils;
import com.example.mysnack_rest.model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductService {
    private final static String ALL_PRODUCTS_QUERY = "select p.id, p.name, p.price, p.qty, p.description, p.manufacturer," +
            " group_concat(i.imgSrc) as images from products as p, images as i where p.id = i.product_id";

    public static Product getProductById(int id) {
        Connection connection = DatabaseConnector.getConnection();
        ResultSet resultSet = DatabaseUtils.getQueryResults(connection, ALL_PRODUCTS_QUERY + " and id = " + id + ";");

        try {
            if (resultSet.next()) {
                return getProductWithResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static ArrayList<Product> getAllProducts() {
        Connection connection = DatabaseConnector.getConnection();
        ResultSet resultSet = DatabaseUtils.getQueryResults(connection, ALL_PRODUCTS_QUERY + " group by p.id;");

        ArrayList<Product> products = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Product product = getProductWithResultSet(resultSet);

                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addProduct(Product product){
        String insertProductQuery = "insert into products (name, price, qty, description, manufacturer)" +
                " values(?, ?, ?, ?, ?);";
        Connection connection = DatabaseConnector.getConnection();

        boolean productInserted = DatabaseUtils.performDBUpdate(connection, insertProductQuery, product.getName(), String.valueOf(product.getPrice()),
                String.valueOf(product.getQty()), product.getDescription(), product.getManufacturer());

        String insertProductImg = "insert into images (product_id, imgSrc) values(?, ?);";
        ResultSet productIdResultSet = DatabaseUtils.getQueryResults(connection, "select max(id) as id from products;");
        String productId = "";
        try{
            if(productIdResultSet.next()){
                productId = productIdResultSet.getString("id");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        boolean image1Inserted = DatabaseUtils.performDBUpdate(connection, insertProductImg, productId,
                product.getImageSrc().split(",")[0]);
        boolean image2Inserted = DatabaseUtils.performDBUpdate(connection, insertProductImg, productId,
                product.getImageSrc().split(",")[1]);

        return productInserted && image1Inserted && image2Inserted;
    }

    public static boolean deleteProduct(int id){
        Connection connection = DatabaseConnector.getConnection();

        String deleteProductQuery = "delete from products where id = ?;";
        boolean deletedFromProducts = DatabaseUtils.performDBUpdate(connection, deleteProductQuery, String.valueOf(id));

        String deleteImagesQuery = "delete from images where product_id = ?;";
        boolean deletedFromImages = DatabaseUtils.performDBUpdate(connection, deleteImagesQuery, String.valueOf(id));

        return deletedFromProducts && deletedFromImages;
    }

    private static Product getProductWithResultSet(ResultSet resultSet) {
        Product product = new Product();
        try {
            product.setId(resultSet.getString("id"));
            product.setName(resultSet.getString("name"));
            product.setPrice(Double.parseDouble(resultSet.getString("price")));
            product.setQty(Integer.parseInt(resultSet.getString("qty")));
            product.setDescription(resultSet.getString("description"));
            product.setManufacturer(resultSet.getString("manufacturer"));
            product.setImages(resultSet.getString("images"));

            return product;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
