package com.kunminx.puremusic.domain.request;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.kunminx.architecture.domain.dispatch.MviDispatcher;
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
        DataRepository.getInstance().downloadFile(event.result.downloadState, dataResult -> {
          if (pageStopped) {
            event.result.downloadState.isForgive = true;
            event.result.downloadState.file = null;
            event.result.downloadState.progress = 0;
          }
          sendResult(event);
        });
        break;
      case DownloadEvent.EVENT_DOWNLOAD_GLOBAL:
        DataRepository.getInstance().downloadFile(event.result.downloadState, dataResult -> {
          sendResult(event);
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
