package com.xiangshangkan.framtest.service;

import com.xiangshangkan.framtest.auto.entity.ListenMusicEntity;

/**
 * @Description: 音乐导入
 * @Author: Zohar
 * @Date: 2020/9/25 20:46
 * @Version: 1.0
 */
public interface ListenMusicService {

    /**
     * 导入音频
     * @param entity
     */
    void synchMusic(ListenMusicEntity entity);

}
