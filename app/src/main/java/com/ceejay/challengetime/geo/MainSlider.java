package com.ceejay.challengetime.geo;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.ChallengeAdapter;
import com.ceejay.challengetime.challenge.ChallengeLoader;
import com.ceejay.challengetime.helper.slider.OptionButton;
import com.ceejay.challengetime.helper.slider.OptionButtonMode;
import com.ceejay.challengetime.helper.slider.Slider;
import com.ceejay.challengetime.main.MainActivity;

/**
 * Created by CJay on 06.02.2015 for Challenge Time.
 */
public class MainSlider extends Slider implements Slider.PanelSlideListener{
    public final static String TAG = MainSlider.class.getSimpleName();

    private Context context;

    private TextView challengeName;
    private TextView challengeType;
    private TextView challengeRecord;

    public MainSlider( Context context ) {
        super(context);
        init(context);
    }

    public MainSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MainSlider(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init( Context context ){
        this.context = context;

        setMaxTopPosition((int) (context.getResources().getDimension(R.dimen.map_header)));
        setPanelSlideListener(this);

        ChallengeAdapter.addOnChallengeFocusChangeListener(new ChallengeAdapter.OnChallengeFocusChangeListener() {
            @Override
            public void onChallengeFocusChange(Challenge challenge) {
                if(challenge != null) {
                    changeButtonMode(OptionButtonMode.WATCH);
                }else{
                    changeButtonMode(OptionButtonMode.REFRESH);
                }
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        challengeName =     (TextView)  ((Activity)context).findViewById(R.id.challengeName);
        challengeType =     (TextView)  ((Activity)context).findViewById(R.id.challengeType);
        challengeRecord =   (TextView)  ((Activity)context).findViewById(R.id.challengeRecord);
    }

    @Override
    public void onPanelCollapsed(View panel) {
        MapManager.unLock();
    }

    @Override
    public void onPanelAnchored(View panel) {
        if (ChallengeAdapter.focusedChallenge != null) {
            MapManager.zoom(ChallengeAdapter.focusedChallenge.position);
            MapManager.lock();
        }
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {}

    @Override
    public void onPanelExpanded(View panel) {}

    @Override
    public void onPanelHidden(View panel) {}

    public void attachButton( final OptionButton button ){
        super.attachView(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (button.getButtonMode()) {
                    case REFRESH:
                        ChallengeAdapter.challenges.clear();
                        MapManager.clearMarker();
                        ChallengeAdapter.addChallenge(ChallengeLoader.load( "brunnen" ));
                        ChallengeAdapter.addChallenge(ChallengeLoader.load( "brunnen2" ));
                        //changeButtonMode(OptionButtonMode.REFRESH_LOAD);
                        break;
                    case REFRESH_LOAD:
                        changeButtonMode(OptionButtonMode.REFRESH);
                        break;
                    case WATCH:
                        challengeName.setText(ChallengeAdapter.focusedChallenge.name);
                        MapManager.showChallengeLayer();
                        changeButtonMode(OptionButtonMode.ACTIVATE);
                        break;
                    case LOCATION:

                        break;
                    case ACTIVATE:
                        ChallengeAdapter.observer.start();
                        changeButtonMode(OptionButtonMode.STOP);
                        break;
                    case STOP:
                        ChallengeAdapter.observer.stop();
                        MapManager.showMarkerLayer();
                        changeButtonMode(OptionButtonMode.REFRESH);
                        break;
                }

            }
        });
    }

    public void clearChallengeEquipment(){
        changeButtonMode( OptionButtonMode.REFRESH );
        setTouchEnabled(false);
        smoothSlideTo(0, 2);
    }

    public void initChallengeEquipment(){
        setTouchEnabled(true);
    }

    public void changeButtonMode( OptionButtonMode buttonMode ){
        if(attachers.size() > 0) {
            changeButtonMode((OptionButton) (attachers.get(0).getView()), buttonMode);
        }
    }

    public void changeButtonMode( OptionButton button , OptionButtonMode bM ){
        button.clearAnimation();
        button.changeType(bM);
    }
}




