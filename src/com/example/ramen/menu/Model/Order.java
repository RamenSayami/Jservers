/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ramen.menu.Model;

import java.io.Serializable;

/**
 *
 * @author shreejal
 * TODO Change UnitPrice to Float
 */
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer TableNo;
	private String DishName;
	private Integer Quantity;
	private OrderStatus Status;
	private Integer UnitPrice;

	public Order() {

	}

//	public Order(Integer TableNo, String DishName, Integer Quantity,
//			OrderStatus Status, Integer UnitPrice) {
//		this.TableNo = TableNo;
//		this.DishName = DishName;
//		this.Quantity = Quantity;
//		this.Status = Status;
//		this.UnitPrice = UnitPrice;
//	}

	public Order(Integer TableNo, String DishName, Integer Quantity,
			OrderStatus status, Integer UnitPrice) {
		this.TableNo = TableNo;
		this.DishName = DishName;
		this.Quantity = Quantity;
		this.Status = status;
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

	public String getStatusString() {
		return Status.toString();
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

	public static OrderStatus castObject(String str) {
		if (str.trim().equals(OrderStatus.CANCELLED.toString())) {
			return OrderStatus.CANCELLED;
		}
		if (str.trim().equals(OrderStatus.COOKED.toString())) {
			return OrderStatus.COOKED;
		}
		if (str.trim().equals(OrderStatus.COOKING.toString())) {
			return OrderStatus.COOKING;
		}
		if (str.trim().equals(OrderStatus.DELIVERED.toString())) {
			return OrderStatus.DELIVERED;
		}
		if (str.trim().equals(OrderStatus.ONBILL.toString())) {
			return OrderStatus.ONBILL;
		}
		if (str.trim().equals(OrderStatus.ORDER_IN.toString())) {
			return OrderStatus.ORDER_IN;
		}
		if (str.trim().equals(OrderStatus.PAID.toString())) {
			return OrderStatus.PAID;
		}
		if (str.trim().equals(OrderStatus.CANCELLED.toString())) {
			return OrderStatus.CANCELLED;
		} else
			return null;
	}

}
