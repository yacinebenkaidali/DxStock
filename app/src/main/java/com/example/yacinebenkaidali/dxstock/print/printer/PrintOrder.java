package com.example.yacinebenkaidali.dxstock.print.printer;

import android.content.Context;
import com.example.yacinebenkaidali.dxstock.print.adapter.USBAdapter;

public class PrintOrder {
	
	public PrintOrder(){
		
	}
	
	public void Print(Context context,String msg){
		USBAdapter usba = new USBAdapter();
			usba.createConn(context);
		try
		{
			usba.printMessage(context,msg);
			usba.closeConnection(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
