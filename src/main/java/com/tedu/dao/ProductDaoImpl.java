package com.tedu.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.tedu.entity.Note;
import com.tedu.entity.Product;
import com.tedu.entity.Result;

@Repository("productDao")//扫描
public class ProductDaoImpl implements ProductDao{

	
	@Resource//注入
	private HibernateTemplate template;
	
	
	@Resource
	private BillDao billDao;
	
	public void save(Product product){
		template.save(product);
	}
	
	public void update(Product product){
		template.update(product);
	}
	
	public void delete(int productId){
		Product product=new Product();
		product.setProductId(productId);
		template.delete(product);
	}
	
	public Product findById(int productId){
		Product product=template.load(Product.class,productId);
		return product;
	}
	
	
	
	public Result<List<Product>> loadProduct(int productId) {
		// TODO Auto-generated method stub
		Result<List<Product>> result=new Result<List<Product>>();
		List<Product> list=new ArrayList<Product>();
		try {
			list.add(findById(productId));
			
			result.setData(list);
			result.setStatus(0);
		} catch (Exception e) {
			// TODO: handle exception
			result.setStatus(1);
		}
		
		
		return result;
	}

	public Result<List<Product>> loadProducts(int userId) {
		// TODO Auto-generated method stub
		Result<List<Product>> result=new Result<List<Product>>();
		List<Product> list=new ArrayList<Product>();
		try {
			String sql="from Product where userId = ?";
			list=template.find(sql,userId);
			result.setData(list);
			result.setStatus(0);
		} catch (Exception e) {
			// TODO: handle exception
			result.setStatus(1);
		}
		
		
		return result;
	}

	public Result<List<Product>> updateProduct(int productId, String name, int price, int totalMoney, String note,
			float rate, Date startdate, Date enddate, int payaccountId,int num) {
		// TODO Auto-generated method stub
		Result<List<Product>> result=new Result<List<Product>>();
		List<Product> list=new ArrayList<Product>();
		int billNum=0;
		try {
			Product product = findById(productId);
			
			if(totalMoney==0){
				billNum=price-product.getPrice();
			}else{
				billNum=totalMoney-product.getTotalMoney();
			}
			
			product.setEnddate(enddate);
			product.setStartdate(startdate);
			product.setName(name);
			product.setNote(note);
			product.setNum(num);
			product.setPayaccountId(payaccountId);
			product.setPrice(price);
			product.setRate(rate);
			product.setStartdate(startdate);
			product.setTotalMoney(totalMoney);
			
			update(product);
			
			if(billNum>0){
				billDao.createBill(0, "", billNum, "", payaccountId, 0);
			}else if(billNum<0){
				billDao.createBill(0, "", -billNum, "", 0, payaccountId);
			}

			result.setStatus(0);
		} catch (Exception e) {
			// TODO: handle exception
			result.setStatus(1);
		}
		
		
		return result;
	}

	public Result<List<Product>> createProduct(String name, int price, int totalMoney, String note, float rate,
			Date startdate, Date enddate, int userId, int payaccountId,int type,int num) {
		// TODO Auto-generated method stub
		Result<List<Product>> result=new Result<List<Product>>();
		List<Product> list=new ArrayList<Product>();
		try {
			Product product = new Product();
			product.setCreatedate(com.tedu.tools.time.getDate());
			product.setType(type);
			product.setEnddate(enddate);
			product.setStartdate(startdate);
			product.setName(name);
			product.setNote(note);
			product.setNum(num);
			product.setPayaccountId(payaccountId);
			product.setPrice(price);
			product.setRate(rate);
			product.setUserId(userId);
			product.setStartdate(startdate);
			product.setTotalMoney(totalMoney);
			
			save(product);

			result.setStatus(0);
		} catch (Exception e) {
			// TODO: handle exception
			result.setStatus(1);
		}
		
		
		return result;
	}

	public Result<List<Product>> deleteProduct(int productId) {
		// TODO Auto-generated method stub
		Result<List<Product>> result=new Result<List<Product>>();
		List<Product> list=new ArrayList<Product>();
		try {

			delete(productId);
			result.setStatus(0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStatus(1);
		}
		
		
		return result;
	}

}
