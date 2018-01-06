package net.sunniwell.gobang.model;

import android.graphics.Point;

import net.sunniwell.jar.log.SWLogger;

import java.util.List;

/**
 * 玩家对战逻辑处理类
 */
public class SWPvpLogicModel extends ASWChessLogicModel {
    private  SWLogger log = SWLogger.getLogger(SWPvpLogicModel.class.getSimpleName());

    @Override
    public boolean restart(int id) {
        log.d("hjx   ==model做逻辑操作==>>>  restart.....    id = "+id);
        return true;
    }

    @Override
    public boolean undo(int id) {
        log.d("hjx   ==model做逻辑操作==>>>  undo.....    id = "+id);
        return true;
    }

    @Override
    public boolean giveup(int id) {
        log.d("hjx   ==model做逻辑操作==>>>  giveup.....     id = "+id);
        return false;
    }

    @Override
    public boolean drawPiece(int id) {
        return true;
    }

    @Override
    public Point playPiece(int x, int y, int depth) {
        return null;
    }
}