package bean.com;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

import android.os.Parcel;
import android.os.Parcelable;

@Table(name = "DataBaseHistroy") // 数据库信息表
public class DataBaseHistory implements Parcelable {

	private int id;
	@Column(column = "isStore")
	public String isStore; //是否收取

	@Column(column = "phone")
	public String phone; // 收件人号码

	@Column(column = "expressname")
	private String expressName; // 快递名称

	@Column(column = "expresscode")
	private String expressCode; // 快递代码

	@Column(column = "expressnumber")
	private String expressNumber; // 快递单号

	@Column(column = "newdate")
	private String newDate; // 最新时间

	@Column(column = "newinfo")
	private String newInfo; // 最新信息

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}

	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	public String getNewDate() {
		return newDate;
	}

	public void setNewDate(String newDate) {
		this.newDate = newDate;
	}

	public String getNewInfo() {
		return newInfo;
	}

	public void setNewInfo(String newInfo) {
		this.newInfo = newInfo;
	}  

	public String getIsStore() {
		return isStore;
	}

	public void setIsStore(String isStore) {
		this.isStore = isStore;
	}



	public static final Parcelable.Creator<DataBaseHistory> CREATOR = new Creator<DataBaseHistory>() {

		@Override
		public DataBaseHistory createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			DataBaseHistory dataBaseHistroy = new DataBaseHistory();
			dataBaseHistroy.setId(source.readInt());
			dataBaseHistroy.setPhone(source.readString());
			dataBaseHistroy.setExpressName(source.readString());
			dataBaseHistroy.setExpressCode(source.readString());
			dataBaseHistroy.setExpressNumber(source.readString());
			dataBaseHistroy.setNewDate(source.readString());
			dataBaseHistroy.setNewInfo(source.readString());
			dataBaseHistroy.setIsStore(source.readString());
			return dataBaseHistroy;
		}

		@Override
		public DataBaseHistory[] newArray(int size) {
			// TODO Auto-generated method stub
			return new DataBaseHistory[size];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

		dest.writeInt(id);
		dest.writeString(phone);
		dest.writeString(expressName);
		dest.writeString(expressCode);
		dest.writeString(expressNumber);
		dest.writeString(newDate);
		dest.writeString(newInfo);
		dest.writeString(isStore);

	}

}