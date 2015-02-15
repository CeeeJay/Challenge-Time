package com.ceejay.challengetime.editor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.helper.math.PointD;
import com.ceejay.challengetime.helper.slider.OptionButton;
import com.ceejay.challengetime.helper.slider.OptionButtonMode;
import com.ceejay.challengetime.helper.slider.Slider;
import com.ceejay.challengetime.helper.slider.SliderAdapter;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by CJay on 06.02.2015 for Challenge Time.
 */
public class EditorSliderAdapter extends SliderAdapter{
    public final static String TAG = EditorSliderAdapter.class.getSimpleName();

    public int optionButtonWidth = (int)context.getResources().getDimension(R.dimen.option_button);
    public int optionButtonMargin = (int)context.getResources().getDimension(R.dimen.option_button_margin);
    public EditorMapManager mapManager;

    private LinearLayout linearLayout;

    public EditorSliderAdapter(Context context , EditorMapManager mManager , Slider slider) {
        super(context, slider);
        this.mapManager = mManager;
        slider.setMaxTopPosition((int) (context.getResources().getDimension(R.dimen.map_header)));

        linearLayout = (LinearLayout) ((Activity)context).findViewById(R.id.optionButtons);

        attachers.add(new Attacher(linearLayout,new Point(
                0,
                optionButtonWidth / 2
        )));
        OptionButton startLocation = new OptionButton(context);
        OptionButton stopLocation = new OptionButton(context);

        startLocation.changeType(OptionButtonMode.STARTLOCATION);
        stopLocation.changeType(OptionButtonMode.STOPLOCATION);

        attachButton(startLocation, new View.OnTouchListener() {

            private PointD startPoint;
            private PointD stopPoint;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if( event.getAction() == MotionEvent.ACTION_DOWN ){
                    startPoint = new PointD(event.getX(),event.getY());
                }
                if( event.getAction() == MotionEvent.ACTION_MOVE ){
                    Projection projection = mapManager.googleMap.getProjection();
                    LatLng latLng = projection.fromScreenLocation(new Point((int)event.getRawX(),(int)event.getRawY()+100));
                    mapManager.setCircle(latLng);
                }
                if( event.getAction() == MotionEvent.ACTION_UP ){
                    stopPoint = new PointD(event.getX(),event.getY());

                }
                return false;
            }
        });
        attachButton( stopLocation , new View.OnTouchListener() {

            private PointD startPoint;
            private PointD stopPoint;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if( event.getAction() == MotionEvent.ACTION_DOWN ){
                    startPoint = new PointD(event.getX(),event.getY());
                }
                if( event.getAction() == MotionEvent.ACTION_MOVE ){
                    Projection projection = mapManager.googleMap.getProjection();
                    LatLng latLng = projection.fromScreenLocation(new Point((int)event.getRawX(),(int)event.getRawY()+100));
                    mapManager.setCircle2(latLng);
                }
                if( event.getAction() == MotionEvent.ACTION_UP ){
                    stopPoint = new PointD(event.getX(),event.getY());

                }
                return false;
            }
        } );

        setUpButtons(slider.getHeight() - (int)context.getResources().getDimension(R.dimen.panel_size));
    }

    @Override
    public void onPanelAnchored(View panel) {
        super.onPanelAnchored(panel);
        mapManager.lock();
    }

    @Override
    public void onPanelCollapsed(View panel) {
        super.onPanelCollapsed(panel);
        mapManager.unLock();
    }

    public void attachButton(OptionButton button , View.OnTouchListener listener) {
        if ( attachers != null && button != null ) {
            attachButton(button);
            button.setOnTouchListener(listener);

        }
    }

    public void attachButton( OptionButton button ) {
        if ( attachers != null && button != null ) {
            linearLayout.addView(button);
            ((ViewGroup.MarginLayoutParams) button.getLayoutParams()).setMargins(optionButtonMargin, 0, optionButtonMargin, 0);
            button.requestLayout();
        }
    }

    @Override
    public void attachButton(OptionButton button, Point offset) {}
}




