package cn.gy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 
 * @author JDChen
 *
 */
@ApiModel("客户信息")
public class AccountPasswordVo {
	@ApiModelProperty("账号")
    private String accountName;
	
	@ApiModelProperty("密码")
	private String password;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "AccountPasswordVo{" +
				"accountName='" + accountName + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
