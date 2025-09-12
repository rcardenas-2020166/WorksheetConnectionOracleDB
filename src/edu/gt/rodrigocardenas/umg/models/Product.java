/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.gt.rodrigocardenas.umg.models;

/**
 *
 * @author Rodrigo Gerardo Cárdenas Monroy
 */
public class Product {
    public int product_id;
    public String name;
    public Float price;
    public String active;
    
    public Product(int product_id, String name, Float price, String active) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.active = active;
    }
}
