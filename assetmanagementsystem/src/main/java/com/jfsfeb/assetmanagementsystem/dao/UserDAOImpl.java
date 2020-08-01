package com.jfsfeb.assetmanagementsystem.dao;

import java.util.ArrayList;
import java.util.List;

import com.jfsfeb.assetmanagementsystem.dto.AssetInfoBean;
import com.jfsfeb.assetmanagementsystem.dto.RequestInfoBeans;
//import com.jfsfeb.assetmanagementsystem.dto.StatusBean;
import com.jfsfeb.assetmanagementsystem.dto.UserInfoBean;
import com.jfsfeb.assetmanagementsystem.exception.AMSException;
import com.jfsfeb.assetmanagementsystem.repository.DataBase;

public class UserDAOImpl implements UserDAO {

	@Override
	public boolean registerUser(UserInfoBean user) {
		for (UserInfoBean u1 : DataBase.USERDB) {
			if ((u1.getEmail()).equals(user.getEmail())) {
				return false;
			}
		}
		DataBase.USERDB.add(user);
		return true;
	}

	@Override
	public UserInfoBean userAuthentication(String email, String password) {
		for (UserInfoBean u2 : DataBase.USERDB) {
			if ((u2.getEmail().equals(email)) && (u2.getPassword().equals(password))) {
				return u2;
			}
		}
		throw new AMSException("Invalid Credentials");
	}

	@Override
	public ArrayList<AssetInfoBean> getAssetInfoBean() {
		return DataBase.ASSETDB;

	}

	@Override
	public boolean requestAsset(int userId, String assetName, int quantity) {

		RequestInfoBeans requestInfo1 = new RequestInfoBeans();
		for (UserInfoBean userBean : DataBase.USERDB) {
			if (userId == userBean.getUserid()) {
				for (AssetInfoBean assetBean : DataBase.ASSETDB) {
					if (assetBean.getAsset_name().equalsIgnoreCase(assetName)) {
						int assetId = assetBean.getAsset_id();
						requestInfo1.setAssetId(assetId);
						requestInfo1.setUserId(userId);
						requestInfo1.setAssetName(assetName);
						requestInfo1.setQuantity(quantity);
						requestInfo1.setUserName(userBean.getUsername());
//						System.out.println(requestInfo);
						DataBase.REQUESTDB.add(requestInfo1);
//						System.out.println(DataBase.REQUESTDB);
						return true;
					}
				}
			}
		}

		throw new AMSException("Request is not sent");
	}

	@Override
	public List<RequestInfoBeans> viewAllAssets(int id) {

		List<RequestInfoBeans> allAssets = new ArrayList<RequestInfoBeans>();

		for (RequestInfoBeans requestBean : DataBase.REQUESTDB) {

			requestBean.getAssetName();
			requestBean.getQuantity();
			if (requestBean.getUserId() == id) {
				allAssets.add(requestBean);
			}
		}
		return allAssets;
	}

}
