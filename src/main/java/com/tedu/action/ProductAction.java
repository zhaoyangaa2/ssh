package com.tedu.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tedu.entity.Note;
import com.tedu.entity.Product;
import com.tedu.entity.Result;
import com.tedu.service.ProductService;

@Controller
@Scope("prototype")
public class ProductAction {
	
	@Resource
	private ProductService productService;
	
	public String loadProduct()
	{
		u=productService.loadProduct(productId);
		return "success";
	}
	
	public String loadProducts()
	{
		u=productService.loadProducts(userId);
		return "success";
	}
	
	public String updateProduct()
	{
		u=productService.updateProduct(productId,name,price,totalMoney,note,rate,startdate,enddate,payaccountId,num);
		return "success";
	}
	
	public String createProduct()
	{
		u=productService.createProduct(name,price,totalMoney,note,rate,startdate,enddate,userId,payaccountId,type,num);
		return "success";
	}
	
	public String deleteProduct()
	{
		u=productService.deleteProduct(productId);
		return "success";
	}
	
	
	
	private  int productId;
	private  String name;
	private  int num;
	private  int price;
	private  int totalMoney;
	private  String note;
	private  float rate;
	private  Date createdate;
	private  Date startdate;
	private  Date enddate;
	private  int userId;
	private  int billId;
	private  int payaccountId;
	private  int type;
	
	private Result<List<Product>> u;
	
	
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(int totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public int getPayaccountId() {
		return payaccountId;
	}
	public void setPayaccountId(int payaccountId) {
		this.payaccountId = payaccountId;
	}

	public Result<List<Product>> getU() {
		return u;
	}

	public void setU(Result<List<Product>> u) {
		this.u = u;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
