package com.studyhelper.baselib.mvp;

/**
 * Create on 2019/3/13.
 * @author jogern
 */
interface OnDelegate {

     <P extends BasePresenter>  P getPresenter();

}
