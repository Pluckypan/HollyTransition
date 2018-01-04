package engineer.echo.transition;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import engineer.echo.transition.cmpts.context.BaseActivity;
import engineer.echo.transition.fragment.AppCtrlFragment;
import engineer.echo.transition.fragment.AppDisplayFragment;

/**
 * MainActivity
 * Created by Plucky<plucky@echo.engineer> on 2018/1/1 下午3:26.
 * more about me: http://www.1991th.com
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //相机预览
        AppDisplayFragment displayFragment = AppDisplayFragment.newInstance();
        transaction.add(R.id.app_display_view, displayFragment);
        //控制层
        AppCtrlFragment ctrlFragment = AppCtrlFragment.newInstance();
        transaction.add(R.id.app_ctrl_view, ctrlFragment);
        transaction.commit();
    }
}
