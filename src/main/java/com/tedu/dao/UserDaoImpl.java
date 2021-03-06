package com.tedu.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.tedu.entity.Account;
import com.tedu.entity.Bill;
import com.tedu.entity.Family;
import com.tedu.entity.Result;
import com.tedu.entity.User;

@Repository("userDao")//扫描
public class UserDaoImpl implements UserDao{
	@Resource//注入
	private HibernateTemplate template;
	
	public void save(User user) {
		template.save(user);
	}

	public void update(User user) {
		template.update(user);
	}

	public void delete(int userId) {
		User user = new User();
		user.setUserId(userId);
		template.delete(user);
	}

	public User findById(int userId) {
		User user = template.load(User.class, userId);
		return user;
	}

	public Result<List<User>> userList() {
		Result<List<User>> u=new Result<List<User>>();
		String hql = "from User";
		List<User> list = template.find(hql);
		if(list.isEmpty()){
			u.setMsg("查询失败");
			u.setStatus(1);//查询失败
		}else {
			u.setData(list);
			u.setMsg("查询成功");
			u.setStatus(0);//查询成功
		}
		return u;
	}


	
	//用户登录
	public Result<List<User>> userLogin(String username, String password) {
		Result<List<User>> u=new Result<List<User>>();
		String hql="from User where username=? and password=?";
		List<User> list=template.find(hql,username,password);
		if(list.isEmpty()){
			u.setStatus(1);//查询失败
		}else {
			u.setData(list);
			u.setStatus(0);//查询成功
		}
		
		return u;
		
	}

	public User findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	//用户注册
	public Result<List<User>> userRegister(String username, String password,String email) {
		// TODO Auto-generated method stub
		Result<List<User>> u=new Result<List<User>>();
		
		String hql="from User where username=? ";
		List<User> list=template.find(hql,username);
		
		if(!list.isEmpty()){
			u.setMsg("用户名已存在！");
			u.setStatus(1);
		}else{
			User user=new User();
			user.setUsername(username);
			user.setPassword(password);
			user.setEmail(email);
			
			int row=0;
			try {
				row=(Integer) template.save(user);
			} catch (Exception e) {
				// TODO: handle exception
				u.setMsg("新增用户失败");
				u.setStatus(1);//新增失败
			}
			
			if(row==1){
				u.setMsg("新增用户成功");
				u.setStatus(0);//新增成功
			}
		}
		
		
		return u;
	}

	public Result<List<Family>> checkFamily(int userId) {
		// TODO Auto-generated method stub
		Result<List<Family>> rs= new Result<List<Family>>();
		List<Family> list= new ArrayList<Family>();
		
		try {
			User user=this.findById(userId);
			
			if(user.getFamilyId()!=0)
			{
				Family family=new Family();
				family.setFamilyId(user.getFamilyId());
				list.add(family);
				rs.setData(list);
				rs.setStatus(0);//该用户拥有家庭
			}else{
				rs.setMsg("请加入家庭！");
				rs.setStatus(2);//该用户没有家庭
			}
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			rs.setStatus(1);
			rs.setMsg("访问失败，请重试！");
			
		}
		
		return rs;
	}

	public Object addFamilyId(int userId, int familyId) {
		// TODO Auto-generated method stub
		try {
			User user=findById(userId);
			user.setFamilyId(familyId);
			update(user);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public Result<List<User>> loadUser(int userId) {
		// TODO Auto-generated method stub
		Result<List<User>> result=new Result<List<User>>();
		List<User> list=new ArrayList<User>();
		
		try {
			User user=findById(userId);
			list.add(user);
			result.setData(list);
			result.setStatus(0);
		} catch (Exception e) {
			// TODO: handle exception
			result.setStatus(1);
		}
		
		return result;
	}

	public Result<List<User>> updateUser(int userId, String email, String password, String truename, String birthday,
			String sex, String mobilephone, String nickname,String usericon,String skin) {
		// TODO Auto-generated method stub
		Result<List<User>> result=new Result<List<User>>();
		
		try {
			User user=findById(userId);
			if(password!=null){
				user.setPassword(password);
			}
			if(nickname!=null)user.setNickname(nickname);
			if(usericon!=null)user.setUsericon(usericon);
			if(birthday!=null)user.setBirthday(birthday);
			if(mobilephone!=null)user.setMobilephone(mobilephone);
			if(email!=null)user.setEmail(email);
			if(truename!=null)user.setTruename(truename);
			if(sex!=null)user.setSex(sex);
			if(skin!=null)user.setSkin(skin);
			update(user);
			
			
			result.setStatus(0);
		} catch (Exception e) {
			// TODO: handle exception
			result.setStatus(1);
		}
		
		return result;
	}

	public Result<List<User>> loadUserTotal(int userId,Date startdate,Date enddate) {
		// TODO Auto-generated method stub
		Result<List<User>> result=new Result<List<User>>();
		List<User> users=new ArrayList<User>();
		List<Account> accounts=new ArrayList<Account>();
		List<Bill> bills=new ArrayList<Bill>();
		try {
			User user=findById(userId);
			String sqlAccounts=" from Account where userId = ? ";
			String sqlBills=" from Bill where (payAccount = ? or receiveAccount = ?) and createdate between ? and ?  and bookId not in (0) ";
			accounts=template.find(sqlAccounts,userId);
			for(Account account:accounts){
				bills=template.find(sqlBills,account.getAccountId(),account.getAccountId(),startdate,enddate);
				for(Bill bill:bills){
					if(bill.getPayAccount()==account.getAccountId()){
						user.setTotalOut(user.getTotalOut()+bill.getNum());
					}else if(bill.getReceiveAccount()==account.getAccountId()){
						user.setTotalIn(user.getTotalIn()+bill.getNum());
					}
				}			
				user.setTotalLeft(user.getTotalLeft()+account.getMoney());
			}
			users.add(user);
			result.setData(users);			
			result.setStatus(0);
		} catch (Exception e) {
			// TODO: handle exception
			result.setStatus(1);
		}
		
		return result;
	}

	public Result<List<User>> changePassword(int userId, String password) {
		// TODO Auto-generated method stub
		Result<List<User>> result=new Result<List<User>>();
		try {
			User user=findById(userId);
			user.setPassword(password);
			save(user);
			result.setStatus(0);
		} catch (Exception e) {
			// TODO: handle exception
			result.setStatus(1);
		}
		
		return result;
	}

	public Result<List<User>> loadUsers() {
		// TODO Auto-generated method stub
		Result<List<User>> result=new Result<List<User>>();
		try {
			String sql="from User where 1=1";
			List<User> list=template.find(sql);
			if(list.size()>0){
				result.setData(list);
			}
			
			result.setStatus(0);
		} catch (Exception e) {
			// TODO: handle exception
			result.setStatus(1);
		}
		
		return result;
	}

	public Result<List<User>> addCollection(int userId, int dataId) {
		// TODO Auto-generated method stub
		Result<List<User>> result=new Result<List<User>>();
		try {
			User user=findById(userId);
			if(user.getCollection()==null){
				user.setCollection(dataId+"");
			}else{
				user.setCollection(user.getCollection()+dataId);
			}
			update(user);
			
			result.setStatus(0);
		} catch (Exception e) {
			// TODO: handle exception
			result.setStatus(1);
		}
		
		return result;
	}

	public Result<List<User>> delCollection(int userId, int dataId) {
		// TODO Auto-generated method stub
		Result<List<User>> result=new Result<List<User>>();
		try {
			User user=findById(userId);
			String co="";
			String[] collection=user.getCollection().split(",");
			for(int i=0;i<collection.length;i++ ){
				if(!collection[i].equals(dataId+"")){
					co+=collection[i]+",";
				}
			}
			co=co.substring(co.length());
			user.setCollection(co);
			update(user);
		
			
			result.setStatus(0);
		} catch (Exception e) {
			// TODO: handle exception
			result.setStatus(1);
		}
		
		return result;
	}

	public Result<List<User>> search(String truename) {
		// TODO Auto-generated method stub
		Result<List<User>> result = new Result<List<User>>();
		List<User> list =new ArrayList<User>();
		try {
			String sql = "from User where truename like ?"; 
			list=template.find(sql,"%"+truename+"%");
			result.setData(list);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(1);
			// TODO: handle exception
		}
		return result;
	}

}
