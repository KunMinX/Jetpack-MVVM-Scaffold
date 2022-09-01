/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kunminx.puremusic.ui.page;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kunminx.architecture.ui.page.BaseFragment;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.page.StateHolder;
import com.kunminx.architecture.ui.state.State;
import com.kunminx.puremusic.BR;
import com.kunminx.puremusic.R;
import com.kunminx.puremusic.data.bean.DownloadState;
import com.kunminx.puremusic.data.config.Const;
import com.kunminx.puremusic.domain.event.DownloadEvent;
import com.kunminx.puremusic.domain.message.DrawerCoordinateManager;
import com.kunminx.puremusic.domain.request.DownloadRequester;

/**
 * Create by KunMinX at 19/10/29
 */
public class SearchFragment extends BaseFragment {
  private SearchStates mStates;
  private DownloadRequester mDownloadRequester;
  private DownloadRequester mGlobalDownloadRequester;

  @Override
  protected void initViewModel() {
    mStates = getFragmentScopeViewModel(SearchStates.class);
    mDownloadRequester = getFragmentScopeViewModel(DownloadRequester.class);
    mGlobalDownloadRequester = getActivityScopeViewModel(DownloadRequester.class);
  }

  @Override
  protected DataBindingConfig getDataBindingConfig() {
    return new DataBindingConfig(R.layout.fragment_search, BR.vm, mStates)
      .addBindingParam(BR.click, new ClickProxy());
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getLifecycle().addObserver(DrawerCoordinateManager.getInstance());
    getLifecycle().addObserver(mDownloadRequester);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mDownloadRequester.output(this, downloadEvent -> {
      if (downloadEvent.eventId == DownloadEvent.EVENT_DOWNLOAD) {
        DownloadState state = downloadEvent.downloadState;
        mStates.progress_cancelable.set(state.progress);
        mStates.enableDownload.set(state.progress == 100 || state.progress == 0);
      }
    });

    mGlobalDownloadRequester.output(this, downloadEvent -> {
      if (downloadEvent.eventId == DownloadEvent.EVENT_DOWNLOAD_GLOBAL) {
        DownloadState state = downloadEvent.downloadState;
        mStates.progress.set(state.progress);
        mStates.enableGlobalDownload.set(state.progress == 100 || state.progress == 0);
      }
    });
  }

  public class ClickProxy {
    public void back() {
      nav().navigateUp();
    }
    public void testNav() {
      openUrlInBrowser(Const.COLUMN_LINK);
    }
    public void subscribe() {
      openUrlInBrowser(Const.COLUMN_LINK);
    }
    public void testDownload() {
      mGlobalDownloadRequester.input(new DownloadEvent(DownloadEvent.EVENT_DOWNLOAD_GLOBAL));
    }
    public void testLifecycleDownload() {
      mDownloadRequester.input(new DownloadEvent(DownloadEvent.EVENT_DOWNLOAD));
    }
  }

  public static class SearchStates extends StateHolder {
    public final State<Integer> progress = new State<>(0);
    public final State<Integer> progress_cancelable = new State<>(0);
    public final State<Boolean> enableDownload = new State<>(true);
    public final State<Boolean> enableGlobalDownload = new State<>(true);
  }
}
