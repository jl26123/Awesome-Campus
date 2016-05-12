package cn.edu.jxnu.awesome_campus.ui.login;

import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;

import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.ui.base.TopNavigationFragment;
import cn.edu.jxnu.awesome_campus.ui.jxnugo.JxnugoUserInfoFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class LoginFragment extends TopNavigationFragment{

    protected static void addChildFragments() {
        if(fragments == null) {
            fragments = new ArrayList<>();
        }else if(fragments.size()>0){
            fragments.clear();
        }
        fragments.add(new EducationLoginFragment());
        fragments.add(new LibraryLoginFragment());
        fragments.add(new JxnuGoLoginFragment());
    }

    public static LoginFragment newInstance(){
        addChildFragments();
        return new LoginFragment();
    }


    @Override
    public void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode()){
            case EVENT.SWIPE_TO_LIBRARY_LOGIN:
                viewPager.setCurrentItem(1);
                pagerAdapter.notifyDataSetChanged();
                break;
            /*case  EVENT.JUMP_TO_JXNUGO_USERINFO:
                Log.d("JXNU_GO","event coming ,go to userinfo");
                fragments.remove(fragments.size()-1);
                fragments.add(new JxnugoUserInfoFragment());
                pagerAdapter.notifyDataSetChanged();
            */
        }
    }


}
