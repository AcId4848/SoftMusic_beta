package com.example.mediaplayer.interfaces;

import java.util.List;
public interface PlayerCallback {
    void onClickPlay(int queueIndex, List<Integer> queue);

    void onClickPlayIndex(int index);
    void onClickPlayNext();
    void onClickPlayPrev();
    void onClickPause();
    void onSetSeekbar(int position);
    void onUpdateQueue(List<Integer> queue, int queueIndex);
    void onDestroy();
}
