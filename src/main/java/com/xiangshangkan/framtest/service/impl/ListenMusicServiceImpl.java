package com.xiangshangkan.framtest.service.impl;

import com.xiangshangkan.framtest.auto.dao.ListenMusicEntityMapper;
import com.xiangshangkan.framtest.auto.dao.ListenMusicListEntityMapper;
import com.xiangshangkan.framtest.auto.entity.ListenMusicEntity;
import com.xiangshangkan.framtest.auto.entity.ListenMusicListEntity;
import com.xiangshangkan.framtest.service.ListenMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description: 导入音乐
 * @Author: Zohar
 * @Date: 2020/9/25 20:47
 * @Version: 1.0
 */
@Service
public class ListenMusicServiceImpl implements ListenMusicService{
    @Autowired
    private ListenMusicEntityMapper musicEntityMapper;
    @Autowired
    private ListenMusicListEntityMapper musicListEntityMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void synchMusic(ListenMusicEntity entity){
        entity.setGmtCreate(new Date());
        entity.setListenCount(0L);
        if (musicEntityMapper.insert(entity) > 0){
            ListenMusicListEntity record = new ListenMusicListEntity();
            record.setListId(3L);
            record.setMusicId(entity.getId());
            record.setGmtCreate(new Date());
            musicListEntityMapper.insert(record);
        }

    }
}
