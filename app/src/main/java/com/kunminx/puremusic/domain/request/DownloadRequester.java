package com.kunminx.puremusic.domain.request;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.kunminx.architecture.domain.dispatch.MviDispatcher;
import com.kunminx.puremusic.data.bean.DownloadState;
import com.kunminx.puremusic.data.repository.DataRepository;
import com.kunminx.puremusic.domain.event.DownloadEvent;
import com.kunminx.puremusic.ui.page.helper.DefaultInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by KunMinX at 20/03/18
 */
public class DownloadRequester extends MviDispatcher<DownloadEvent> {

  private Disposable mDisposable;

  @Override
  protected void onHandle(DownloadEvent event) {
    switch (event.eventId) {
      case DownloadEvent.EVENT_DOWNLOAD:
        DataRepository.getInstance().downloadFile()
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new DefaultInterface.Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
              mDisposable = d;
            }
            @Override
            public void onNext(Integer integer) {
              sendResult(event.copy(new DownloadState(true, integer)));
            }
          });
        break;
      case DownloadEvent.EVENT_DOWNLOAD_GLOBAL:
        DataRepository.getInstance().downloadFile()
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe((DefaultInterface.Observer<Integer>) integer -> {
            sendResult(event.copy(new DownloadState(true, integer)));
          });
        break;
    }
  }

  @Override
  public void onStop(@NonNull LifecycleOwner owner) {
    super.onStop(owner);
    if (mDisposable != null && !mDisposable.isDisposed()) {
      mDisposable.dispose();
      mDisposable = null;
    }
  }
}
