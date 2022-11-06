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

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kunminx.architecture.ui.page.BaseFragment;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.page.StateHolder;
import com.kunminx.architecture.ui.state.State;
import com.kunminx.player.domain.PlayerEvent;
import com.kunminx.puremusic.BR;
import com.kunminx.puremusic.R;
import com.kunminx.puremusic.data.bean.TestAlbum;
import com.kunminx.puremusic.domain.event.Messages;
import com.kunminx.puremusic.domain.message.PageMessenger;
import com.kunminx.puremusic.domain.request.MusicRequester;
import com.kunminx.puremusic.domain.proxy.PlayerManager;
import com.kunminx.puremusic.ui.page.adapter.PlaylistAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by KunMinX at 19/10/29
 */
public class MainFragment extends BaseFragment {
  private MainStates mStates;
  private PageMessenger mMessenger;
  private MusicRequester mMusicRequester;
  private PlaylistAdapter mAdapter;

  @Override
  protected void initViewModel() {
    mStates = getFragmentScopeViewModel(MainStates.class);
    mMessenger = getApplicationScopeViewModel(PageMessenger.class);
    mMusicRequester = getFragmentScopeViewModel(MusicRequester.class);
  }

  @Override
  protected DataBindingConfig getDataBindingConfig() {
    mAdapter = new PlaylistAdapter(getContext());
    mAdapter.setOnItemClickListener((viewId, item, position) -> {
      PlayerManager.getInstance().playAudio(position);
    });

    return new DataBindingConfig(R.layout.fragment_main, BR.vm, mStates)
      .addBindingParam(BR.click, new ClickProxy())
      .addBindingParam(BR.adapter, mAdapter);
  }

  @SuppressLint("NotifyDataSetChanged")
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    PlayerManager.getInstance().getDispatcher().output(this, playerEvent -> {
      if (playerEvent.eventId == PlayerEvent.EVENT_CHANGE_MUSIC) {
        mAdapter.notifyDataSetChanged();
      }
    });

    mMusicRequester.getFreeMusicsResult().observe(getViewLifecycleOwner(), dataResult -> {
      if (!dataResult.getResponseStatus().isSuccess()) return;

      TestAlbum musicAlbum = dataResult.getResult();
      if (musicAlbum != null && musicAlbum.getMusics() != null) {
        mStates.list.set(musicAlbum.getMusics());
        PlayerManager.getInstance().loadAlbum(musicAlbum);
      }
    });

    if (PlayerManager.getInstance().getAlbum() == null) mMusicRequester.requestFreeMusics();
  }

  public class ClickProxy {
    public void openMenu() {
      mMessenger.input(new Messages(Messages.EVENT_OPEN_DRAWER));
    }
    public void login() {
      nav().navigate(R.id.action_mainFragment_to_loginFragment);
    }
    public void search() {
      nav().navigate(R.id.action_mainFragment_to_searchFragment);
    }
  }

  public static class MainStates extends StateHolder {
    public final State<Boolean> initTabAndPage = new State<>(true);
    public final State<String> pageAssetPath = new State<>("summary.html");
    public final State<List<TestAlbum.TestMusic>> list = new State<>(new ArrayList<>());
  }
}
