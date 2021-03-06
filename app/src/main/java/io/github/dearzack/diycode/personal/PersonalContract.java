package io.github.dearzack.diycode.personal;

import com.gcssloop.diycode_sdk.api.user.event.GetUserCreateTopicListEvent;
import com.gcssloop.diycode_sdk.api.user.event.GetUserEvent;

import io.github.dearzack.diycode.base.BasePresenter;
import io.github.dearzack.diycode.base.BaseView;

/**
 * Created by Zack on 2017/6/21.
 */

public interface PersonalContract {

    interface View extends BaseView<Presenter> {
        void onGetUser(GetUserEvent event);
        void onGetTopics(GetUserCreateTopicListEvent event);
    }

    interface Presenter extends BasePresenter {
        void getUser(String loginName);
        void getTopics(String loginName, int offset, int limit);
    }
}
