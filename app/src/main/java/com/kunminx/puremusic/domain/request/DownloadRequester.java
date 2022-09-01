package com.kunminx.puremusic.domain.request;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.kunminx.architecture.domain.dispatch.MviDispatcher;
import com.kunminx.puremusic.data.bean.DownloadState;
import com.kunminx.puremusic.data.repository.DataRepository;
import com.kunminx.puremusic.domain.event.DownloadEvent;

/**
 * Create by KunMinX at 20/03/18
 */
public class DownloadRequester extends MviDispatcher<DownloadEvent> {

  private boolean pageStopped;

  @Override
  protected void onHandle(DownloadEvent event) {
    switch (event.eventId) {
      case DownloadEvent.EVENT_DOWNLOAD:
        DataRepository.getInstance().downloadFile(dataResult -> {
          if (pageStopped) {
            sendResult(event.copy(new DownloadState(true, 0, null)));
          } else {
            sendResult(event.copy(dataResult.getResult()));
          }
        });
        break;
      case DownloadEvent.EVENT_DOWNLOAD_GLOBAL:
        DataRepository.getInstance().downloadFile(dataResult -> {
          sendResult(event.copy(dataResult.getResult()));
        });
        break;
    }
  }

  @Override
  public void onCreate(@NonNull LifecycleOwner owner) {
    super.onCreate(owner);
    pageStopped = false;
  }

  @Override
  public void onStop(@NonNull LifecycleOwner owner) {
    super.onStop(owner);
    pageStopped = true;
  }
}
