package whatsapp.rest.com.br.whatsapp.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import whatsapp.rest.com.br.whatsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {


    @BindView(R.id.tab) public TabLayout tabLayout;
    @BindView(R.id.vp_content) ViewPager viewPager;
    public TabFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);

        ButterKnife.bind( this, view );

        viewPager.setAdapter( new TabAdapter( getFragmentManager()) );

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager( viewPager );
            }
        });

        return view;
    }

    private class TabAdapter extends FragmentStatePagerAdapter {

        String[] tabs = { "CONVERSAS", "CONTATOS" };

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch ( position ){
                case 0:
                    fragment = new ConversasFragment();
                    break;
                case 1:
                    fragment = new ContatosFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[ position ];
        }
    }
}
