package com.Taskmanagement.ui.kpt;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.Taskmanagement.entity.KptEntity;
import com.Taskmanagement.entity.KptHstryEntity;
import com.Taskmanagement.entity.KptLinkEntity;
import com.Taskmanagement.entity.TagEntity;
import com.Taskmanagement.repository.KptRepository;

import java.time.LocalDateTime;

public class KptViewModel extends AndroidViewModel {
    private final KptRepository repository;

    public KptViewModel(@NonNull Application application) {
        super(application);
        repository = new KptRepository(application);
    }

// ================================================================
// DB操作_INSERT
// ================================================================
    /**
     * kpt_table登録
     *
     */
    public void insertKptEntity(
            String kptId,
            String kptNm,
            String kptDtl,
            LocalDateTime nowDttm
    ) {
        KptEntity entity = new KptEntity(
                kptId,
                kptNm,
                kptDtl,
                null,
                nowDttm,
                nowDttm
        );
        repository.insert(entity);
    }
    /**
     * kpt_history_table登録
     *
     */
    public void insertKptHstryEntity(
            String kptId,
            String kptType,
            LocalDateTime nowDttm
    ) {
        KptHstryEntity entity = new KptHstryEntity(
                kptId,
                0, // 後続で最大値+1の値を入れるため、暫定で0を入れておく
                kptType,
                null,
                nowDttm,
                nowDttm
        );
        repository.insertWithSequence(entity);
    }
    /**
     * kpt_link_table登録
     *
     */
    public void insertKptLinkEntity(
            String kptId_P,
            String kptId_Kt,
            LocalDateTime nowDttm
    ) {
        KptLinkEntity entity = new KptLinkEntity(
                kptId_P,
                kptId_Kt,
                nowDttm,
                nowDttm
        );
        repository.insert(entity);
    }
    /**
     * tag_table登録
     *
     */
    public void insertTagEntity(
            String tskKptId,
            String tagId,
            LocalDateTime nowDttm
    ) {
        TagEntity entity = new TagEntity(
                tskKptId,
                tagId,
                nowDttm,
                nowDttm
        );
        repository.insert(entity);
    }

// ================================================================
// DB操作_UPDATE
// ================================================================

// ================================================================
// DB操作_SELECT
// ================================================================
    public KptEntity getAaa() {
        return repository.getAaa();
    }

// ================================================================
// 画面独自
// ================================================================

// ================================================================
// 多画面共通
// ================================================================

}