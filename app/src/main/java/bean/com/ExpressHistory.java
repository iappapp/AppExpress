package bean.com;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

import android.os.Parcel;
import android.os.Parcelable;

@Table(name = "expresshistory") // 我的快递保存数据库
public class ExpressHistory implements Parcelable {

	private int id;

	@Column(column = "name")
	public String name; // 备注名字

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public static final Parcelable.Creator<ExpressHistory> CREATOR = new Creator<ExpressHistory>() {

		@Override
		public ExpressHistory createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			ExpressHistory expressHistory = new ExpressHistory();
			expressHistory.setId(source.readInt());
			expressHistory.setName(source.readString());
			expressHistory.setExpressName(source.readString());
			expressHistory.setExpressCode(source.readString());
			expressHistory.setExpressNumber(source.readString());
			expressHistory.setNewDate(source.readString());
			expressHistory.setNewInfo(source.readString());
			return expressHistory;
		}

		@Override
		public ExpressHistory[] newArray(int size) {
			// TODO Auto-generated method stub
			return new ExpressHistory[size];
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
		dest.writeString(name);
		dest.writeString(expressName);
		dest.writeString(expressCode);
		dest.writeString(expressNumber);
		dest.writeString(newDate);
		dest.writeString(newInfo);

	}

}