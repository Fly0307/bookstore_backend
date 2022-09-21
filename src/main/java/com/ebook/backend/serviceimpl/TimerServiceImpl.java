package com.ebook.backend.serviceimpl;

import com.ebook.backend.service.TimerService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("session")
public class TimerServiceImpl implements TimerService {
    private long starttime = 0;
    private long endtime=0;

    @Override
    public void StartCountTime() {
        starttime=System.currentTimeMillis();
    }

    @Override
    public long GetTime() {
        endtime=System.currentTimeMillis();
        long count=endtime-starttime;
        return count;
    }

}
