package com.Taskmanagement.ui.masterDataCategoryList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.Taskmanagement.entity.CgryEntity;
import com.Taskmanagement.entity.item.MainItem;
import com.Taskmanagement.ui.base.DispTskBaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MasterDataCategoryListViewModel extends DispTskBaseViewModel {

    public MasterDataCategoryListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<CgryEntity>> getCategoryList() {
        return repository.getCategoryList(true);
    }

    public List<MainItem> getItemList(List<CgryEntity> cgryList) {
        List<MainItem> mainItemList = new ArrayList<>();
//        List<CgryEntity> categoryEntityList = getCategoryList();
//        if (categoryEntityList == null) {
//            categoryEntityList = new ArrayList<>();
//        }

        String mainName = "";
        List<String> subList = null;
        for (CgryEntity category : cgryList) {
            if (!mainName.equals(category.mainName)) {
                subList = new ArrayList<>();
                mainItemList.add(new MainItem(category.mainName, subList, false));
                mainName = category.mainName;
            }
            subList.add(category.subName);
        }
        // ダミーデータ
//        mainItemList.add(new MainItem("P1", Arrays.asList("P1_1", "P1_2", "P1_3"), false));
//        mainItemList.add(new MainItem("P2", Arrays.asList("P2_1"), false));
//        mainItemList.add(new MainItem("P3", Arrays.asList("P3_1", "P3_2"), false));
        return mainItemList;
    }
}