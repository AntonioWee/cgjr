package cgncjr.com.cgncjr.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cgncjr.com.cgncjr.R;


/**
 * Created by Administrator on 2016/5/17.
 */
public class ChoiceEnterActivity extends Activity {

    @Bind(R.id.ll_nongzifenqi)
    LinearLayout ll_nongzifenqi;

    @Bind(R.id.yumiaofenqi)
    LinearLayout yumiaofenqi;

    @Bind(R.id.ll_miaomufenqi)
    LinearLayout ll_miaomufenqi;

    @Bind(R.id.ll_jiadianfenqi)
    LinearLayout ll_jiadianfenqi;

    @Bind(R.id.ll_nongjifenqi)
    LinearLayout ll_nongjifenqi;

    @Bind(R.id.ll_tujianfenqi)
    LinearLayout ll_tujianfenqi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_enter);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.ll_nongzifenqi)
    public void choiceClick_nongzi(){
        startActivity(new Intent(ChoiceEnterActivity.this, MainActivity.class));
        ChoiceEnterActivity.this.finish();
        overridePendingTransition(R.anim.translate_enter_from_right, R.anim.translate_exit_to_left);
    }

    @OnClick(R.id.yumiaofenqi)
    public void choiceClick_yumiao(){
        startActivity(new Intent(ChoiceEnterActivity.this, MainActivity.class));
        ChoiceEnterActivity.this.finish();
        overridePendingTransition(R.anim.translate_enter_from_right, R.anim.translate_exit_to_left);
    }

    @OnClick(R.id.ll_miaomufenqi)
    public void choiceClick_miaomu(){
        startActivity(new Intent(ChoiceEnterActivity.this, MainActivity.class));
        ChoiceEnterActivity.this.finish();
        overridePendingTransition(R.anim.translate_enter_from_right, R.anim.translate_exit_to_left);
    }

    @OnClick(R.id.ll_jiadianfenqi)
    public void choiceClick_jiadian(){
        startActivity(new Intent(ChoiceEnterActivity.this, MainActivity.class));
        ChoiceEnterActivity.this.finish();
        overridePendingTransition(R.anim.translate_enter_from_right, R.anim.translate_exit_to_left);
    }


    @OnClick(R.id.ll_nongjifenqi)
    public void choiceClick_nongji(){
        startActivity(new Intent(ChoiceEnterActivity.this, MainActivity.class));
        ChoiceEnterActivity.this.finish();
        overridePendingTransition(R.anim.translate_enter_from_right, R.anim.translate_exit_to_left);
    }


    @OnClick(R.id.ll_tujianfenqi)
    public void choiceClick_tujian(){
        startActivity(new Intent(ChoiceEnterActivity.this, MainActivity.class));
        ChoiceEnterActivity.this.finish();
        overridePendingTransition(R.anim.translate_enter_from_right, R.anim.translate_exit_to_left);
    }

    @OnClick(R.id.iv_guanbi)
    public void choiceClick_guanbi(){

        startMainActivty();


    }

    private void startMainActivty() {
        Intent intent=new Intent(ChoiceEnterActivity.this, MainActivity.class);
        //intent.putExtra("ClientChoice",clientChoice);
        startActivity(intent);
        ChoiceEnterActivity.this.finish();
        overridePendingTransition(R.anim.translate_enter_from_right, R.anim.translate_exit_to_left);


    }


}
