package cc.linkedme.dao.webapi;

import cc.linkedme.data.model.ButtonInfo;

import java.util.List;

/**
 * Created by LinkedME01 on 16/4/7.
 */
public interface ButtonDao {
    int insertButton(ButtonInfo buttonInfo);
    ButtonInfo getButtonInfo(String btnId);
    List<ButtonInfo> getButtonListByBtnId(String btnId);
    List<ButtonInfo> getButtonListByAppId(long appId, boolean isAll);
    boolean updateButton(ButtonInfo buttonInfo);
    boolean deleteButton(String btnId);
}
