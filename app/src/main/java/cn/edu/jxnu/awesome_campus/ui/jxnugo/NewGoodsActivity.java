package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.model.jxnugo.CommentModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsPhotoModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.support.adapter.jxnugo.CommentListAdapter;
import cn.edu.jxnu.awesome_campus.support.adapter.jxnugo.GoodsListAdapter;
import cn.edu.jxnu.awesome_campus.support.adapter.jxnugo.GoodsPicListAdapter;
import cn.edu.jxnu.awesome_campus.support.loader.FrescoImageLoader;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by KevinWu on 16-5-13.
 */
public class NewGoodsActivity extends BaseToolbarActivity implements View.OnClickListener{
    public static final String TAG="NewGoodsActivity";
    private static final String title="发布二手商品";
    private final int REQUEST_CODE_GALLERY = 1001;
    private Button image;
    private GoodsPhotoModel model;
    private List<GoodsPhotoModel> mPhotoList;
    private BaseListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
        setContentView(R.layout.activity_jxnugo_new_goods);
        initToolbar();
        initView();
        bindAdapter();
        setToolbarTitle(title);
        image=(Button)findViewById(R.id.addPic);
        image.setOnClickListener(this);

//        callImageSelector();
    }
    private void initView() {
        recyclerView=(RecyclerView)findViewById(R.id.addPhotoRV);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
    }
    private void bindAdapter() {
        model=new GoodsPhotoModel();
        adapter=new GoodsPicListAdapter(NewGoodsActivity.this,model);
        recyclerView.setAdapter(adapter);
    }
    private void callImageSelector() {
        mPhotoList = new ArrayList<>();
        ThemeConfig themeConfig = null;
        themeConfig = ThemeConfig.DARK;
        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
        cn.finalteam.galleryfinal.ImageLoader imageLoader;
        imageLoader = new FrescoImageLoader(NewGoodsActivity.this);
        functionConfigBuilder.setMutiSelectMaxSize(3);
        functionConfigBuilder.setEnableEdit(false);
        functionConfigBuilder.setEnableCamera(true);
        functionConfigBuilder.setEnablePreview(true);
        final FunctionConfig functionConfig = functionConfigBuilder.build();
        CoreConfig coreConfig = new CoreConfig.Builder(NewGoodsActivity.this, imageLoader, themeConfig)
                .setFunctionConfig(functionConfig)
//                .setPauseOnScrollListener(pauseOnScrollListener)
                .setNoAnimcation(true)
                .build();
        GalleryFinal.init(coreConfig);
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
//        startActivityForResult(intent, REQUEST_IMAGE);
        initFresco();
    }


    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                for(int i=0;i<resultList.size();i++){
                    mPhotoList.add(new GoodsPhotoModel(resultList.get(i).getPhotoId(),
                            resultList.get(i).getPhotoPath(),
                            resultList.get(i).getWidth(),
                            resultList.get(i).getHeight()));
                }
                adapter.newList(mPhotoList);
//                mChoosePhotoListAdapter.notifyDataSetChanged();
                Log.d(TAG,"取得的图片的数量为："+resultList.size());
                Log.d(TAG,"目标图片数量为："+mPhotoList.size());
                Log.d(TAG,"第一个图片路劲为"+mPhotoList.get(0).getPhotoPath());
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(InitApp.AppContext, TAG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addPic:
                callImageSelector();
                break;
        }
    }


    private void initFresco() {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setBitmapsConfig(Bitmap.Config.ARGB_8888)
                .build();
        Fresco.initialize(this, config);
    }
}
