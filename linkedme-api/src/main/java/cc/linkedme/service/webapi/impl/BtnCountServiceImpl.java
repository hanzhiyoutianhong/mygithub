package cc.linkedme.service.webapi.impl;

import cc.linkedme.dao.webapi.BtnCountDao;
import cc.linkedme.data.model.ButtonCount;
import cc.linkedme.service.webapi.BtnCountService;

import javax.annotation.Resource;

/**
 * Created by vontroy on 7/14/16.
 */
public class BtnCountServiceImpl implements BtnCountService {

    @Resource
    private BtnCountDao btnCountDao;
    public int addButtonCount(ButtonCount buttonCount) {
        return btnCountDao.addButtonCount(buttonCount);
    }
}
