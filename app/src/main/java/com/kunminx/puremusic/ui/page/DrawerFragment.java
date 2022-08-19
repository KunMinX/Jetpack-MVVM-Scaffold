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
import com.kunminx.puremusic.data.bean.LibraryInfo;
import com.kunminx.puremusic.data.config.Const;
import com.kunminx.puremusic.domain.request.InfoRequester;
import com.kunminx.puremusic.ui.page.adapter.DrawerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by KunMinX at 19/10/29
 */
public class DrawerFragment extends BaseFragment {
  private DrawerStates mStates;
  private InfoRequester mInfoRequester;

  @Override
  protected void initViewModel() {
    mStates = getFragmentScopeViewModel(DrawerStates.class);
    mInfoRequester = getFragmentScopeViewModel(InfoRequester.class);
  }

  @Override
  protected DataBindingConfig getDataBindingConfig() {
    return new DataBindingConfig(R.layout.fragment_drawer, BR.vm, mStates)
      .addBindingParam(BR.click, new ClickProxy())
      .addBindingParam(BR.adapter, new DrawerAdapter(getContext()));
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mInfoRequester.getLibraryResult().observe(getViewLifecycleOwner(), dataResult -> {
      if (!dataResult.getResponseStatus().isSuccess()) return;
      if (dataResult.getResult() != null) mStates.list.set(dataResult.getResult());
    });
    mInfoRequester.requestLibraryInfo();
  }

  public class ClickProxy {
    public void logoClick() {
      openUrlInBrowser(Const.PROJECT_LINK);
    }
  }

  public static class DrawerStates extends StateHolder {
    public final State<List<LibraryInfo>> list = new State<>(new ArrayList<>());
  }
}
