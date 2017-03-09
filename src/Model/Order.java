/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

import jservers.OrderStatus;

/**
 *
 * @author shreejal
 */
public class Order implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private Integer TableNo;
    private String DishName;
    private Integer Quantity;
    private OrderStatus Status;
    private Integer UnitPrice;
    
    public Order(){
    	
    }
    
    public Order(Integer TableNo, String DishName, Integer Quantity, OrderStatus Status, Integer UnitPrice) {
        this.TableNo = TableNo;
        this.DishName = DishName;
        this.Quantity = Quantity;
        this.Status = Status;
        this.UnitPrice = UnitPrice;
    }

    public Order(Integer TableNo, String DishName, Integer Quantity, String Status, Integer UnitPrice) {
        this.TableNo = TableNo;
        this.DishName = DishName;
        this.Quantity = Quantity;
        this.Status = castObject(Status);
        this.UnitPrice = UnitPrice;
    }

    public Integer getTableNo() {
        return TableNo;
    }

    public void setTableNo(Integer TableNo) {
        this.TableNo = TableNo;
    }

    public String getDishName() {
        return DishName;
    }

    public void setDishName(String DishName) {
        this.DishName = DishName;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer Quantity) {
        this.Quantity = Quantity;
    }

    public OrderStatus getStatus() {
        return Status;
    }

    public void setStatus(OrderStatus Status) {
        this.Status = Status;
    }

    public Integer getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(Integer UnitPrice) {
        this.UnitPrice = UnitPrice;
    }
    
    public static OrderStatus castObject(String str){
    	if(str.equals(OrderStatus.CANCELLED)){
    		return OrderStatus.CANCELLED;
    	}
    	if(str.equals(OrderStatus.COOKED)){
    		return OrderStatus.COOKED;
    	}
    	if(str.equals(OrderStatus.COOKING)){
    		return OrderStatus.COOKING;
    	}
    	if(str.equals(OrderStatus.DELIVERED)){
    		return OrderStatus.DELIVERED;
    	}
    	if(str.equals(OrderStatus.ONBILL)){
    		return OrderStatus.ONBILL;
    	}
    	if(str.equals(OrderStatus.ORDER_IN)){
    		return OrderStatus.ORDER_IN;
    	}
    	if(str.equals(OrderStatus.PAID)){
    		return OrderStatus.PAID;
    	}
    	if(str.equals(OrderStatus.CANCELLED)){
    		return OrderStatus.CANCELLED;
    	}
    	else return null;
    }
    
    
}
