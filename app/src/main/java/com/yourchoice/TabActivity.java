package com.yourchoice;

import java.util.Locale;


import android.app.Dialog;

import android.graphics.Typeface;

import android.os.Bundle;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import com.yourchoice.clases.CustomTypefaceSpan;
import com.yourchoice.clases.ModeloSistema;


public final class TabActivity extends AppCompatActivity implements ActionBar.TabListener {


    private Dialog dialog;
    private Typeface typeFace;
    private SpannableStringBuilder ss = new SpannableStringBuilder("YourChoice");
    private ActionBar actionBar;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ModelaActivity modela = new ModelaActivity();
    private AgregaActivity agrega = new AgregaActivity();
    private DecideActivity decide = new DecideActivity();
    private ModeloSistema modeloSistema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        typeFace = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        ss.setSpan(new CustomTypefaceSpan("", typeFace), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        actionBar = this.getSupportActionBar();
        actionBar.setTitle(ss);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        Bundle bundle = this.getIntent().getExtras();
        modeloSistema = (ModeloSistema) bundle.getSerializable("modelo");
        modela.setModeloSistema(modeloSistema);
        agrega.setModeloSistema(modeloSistema);
        decide.setModeloSistema(modeloSistema);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        ActionBar.Tab tab;
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            tab = actionBar.newTab();
            tab.setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this);
            actionBar.addTab(tab);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("modelo", modeloSistema);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            if (modeloSistema == null) {
                modeloSistema = (ModeloSistema) savedInstanceState.getSerializable("modelo");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tab, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_info) {
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_acerca);
            TextView texto = (TextView) dialog.findViewById(R.id.texto_copy);
            SpannableStringBuilder ssAbout = new SpannableStringBuilder(texto.getText());
            ssAbout.setSpan(new CustomTypefaceSpan("", typeFace), 0,
                    ssAbout.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            texto.setText(ssAbout);
            Button dialogCLOSEButton = (Button) dialog.findViewById(R.id.btn_cancel_about);
            // dialogCLOSEButton.setTypeface(typeFace);
            dialogCLOSEButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
            return true;
        }
       /* if (id == R.id.action_salir) {
            Intent intent = new Intent(this, InicioActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("finishApplication", true);
            startActivity(intent);
            finish();
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
        if (tab.getPosition() == 0) {
        } else {
            if (tab.getPosition() == 1) {
            } else {
                if (tab.getPosition() == 2) {
                }
            }
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return modela;
            } else {
                if (position == 1) {
                    return agrega;
                } else {
                    if (position == 2) {
                        return decide;
                    } else {
                        return new Fragment();
                    }
                }
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);

            }
            return null;
        }
    }


}
