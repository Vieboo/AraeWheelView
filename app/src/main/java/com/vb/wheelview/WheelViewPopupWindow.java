package com.vb.wheelview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;

import com.vb.wheelview.wheel.ArrayWheelAdapter;
import com.vb.wheelview.wheel.OnWheelChangedListener;
import com.vb.wheelview.wheel.OnWheelScrollListener;
import com.vb.wheelview.wheel.WheelView;


public class WheelViewPopupWindow extends PopupWindow implements OnClickListener {
	
	public interface OnWVPWClickListener {
		public void onSubmitClick(int position);
	}
	
	private Context mContext;
	private WheelView wheel_view;
	private View shadow;
	private ArrayWheelAdapter<String> adapter;

	private String[] itemArray;
	private OnWVPWClickListener wVPWClickListener;


	public WheelViewPopupWindow(Context context, String[] items){
		super(context);
		this.itemArray = items;
		this.mContext = context;
		LayoutInflater layoutInflater = LayoutInflater.from(mContext);
		View contentView = layoutInflater.inflate(R.layout.layout_pop_wheelview, null);
		this.setContentView(contentView);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.MATCH_PARENT);
		this.setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		this.setBackgroundDrawable(dw);
		this.setOutsideTouchable(true);

		shadow = contentView.findViewById(R.id.pop_wheelview_shadow);
		shadow.setOnClickListener(this);

		wheel_view = (WheelView) contentView.findViewById(R.id.wheel_view);
		wheel_view.setCyclic(false);

		adapter = new ArrayWheelAdapter<String>(mContext, itemArray);
		adapter.setTextSize(18);
		adapter.setTextPadding(10);
		adapter.setSelectPosition(0);
		wheel_view.setViewAdapter(adapter);
		wheel_view.setCurrentItem(0);

		wheel_view.addScrollingListener(new OnWheelScrollListener() {
			@Override
			public void onScrollingStarted(WheelView wheel) {

			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				adapter.setSelectPosition(wheel.getCurrentItem());
			}
		});
	}
	
	public void setOnWVPWClickListener (OnWVPWClickListener listener){
		this.wVPWClickListener = listener;
	}
	

	public void showPop(View v) {
		if(!isShowing()) {
			showAtLocation(v, 0, 0, Gravity.NO_GRAVITY);
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.pop_wheelview_shadow:
				if (this.isShowing()) {
					this.dismiss();
				}
				break;
		}
	}

}
