package com.Taskmanagement.ui.masterDataCategoryEdit;

import android.app.Application;

import androidx.annotation.NonNull;

import com.Taskmanagement.entity.CgryEntity;
import com.Taskmanagement.repository.TaskRepository;
import com.Taskmanagement.ui.base.DispTskBaseViewModel;

import java.time.LocalDateTime;
import java.util.UUID;

public class MasterDataCategoryEditViewModel extends DispTskBaseViewModel {
//    protected final TaskRepository repository = null; // TODO 別クラス作る？

    public MasterDataCategoryEditViewModel(@NonNull Application application) {
        super(application);
    }

// ================================================================
// DB操作_INSERT
// ================================================================
    /**
     * category_table登録
     *
     * @param cgryType カテゴリータイプ（1：レビュー、2：KPT）
     * @param mainCgry メインカテゴリー
     * @param subCgry サブカテゴリー
     */
    public void insertCategoryEntity(
            String cgryType,
            String mainCgry,
            String subCgry
    ) {
        String cgryId = UUID.randomUUID().toString();
        LocalDateTime nowDttm = LocalDateTime.now();
        CgryEntity entity = new CgryEntity(
                cgryId,
                cgryType,
                mainCgry,
                subCgry,
                false,
                nowDttm,
                nowDttm
        );
        repository.insert(entity);
    }
}