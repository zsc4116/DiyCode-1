package io.github.dearzack.diycode.homepage;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseFragment;


public class HomepageFragment extends BaseFragment implements HomepageContract.View {

    public static final int PAGE_TOPIC = 0;
    public static final int PAGE_NEW = 1;

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.vp_view)
    ViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private View rootView;

    @Inject
    String[] titles;

    @Inject
    List<Fragment> fragments;

    @Inject
    HomepagePresenter presenter;




    public HomepageFragment() {
        // Required empty public constructor
    }

    public static HomepageFragment newInstance() {
        HomepageFragment fragment = new HomepageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_homepage, container, false);
            ButterKnife.bind(this, rootView);
            DaggerHomepageComponent.builder()
                    .homepagePresenterModule(new HomepagePresenterModule(this))
                    .build()
                    .inject(this);
            initView(rootView);
        }
        // 缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，
        // 要不然会发生这个rootView已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
//        presenter.getNodes();
        return rootView;
    }

    private void initView(View rootView) {
        viewPager.setAdapter(new HomepageFragmentAdapter(fragments,
                titles, getActivity().getSupportFragmentManager(), getActivity()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 1) {
                    fab.setScaleX(1 - positionOffset);
                    fab.setScaleY(1 - positionOffset);
                    fab.setAlpha(1 - positionOffset);
                } else if (position == 0 && fab.getAlpha() < 1) {
                    fab.setScaleX(1 - positionOffset);
                    fab.setScaleY(1 - positionOffset);
                    fab.setAlpha(1 - positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabs.setupWithViewPager(viewPager);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setPresenter(HomepageContract.Presenter presenter) {

    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        presenter.add(viewPager.getCurrentItem(), getActivity());
    }


}
