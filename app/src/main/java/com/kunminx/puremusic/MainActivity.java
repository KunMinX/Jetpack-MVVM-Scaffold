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

package com.kunminx.puremusic;

import android.os.Bundle;
import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.kunminx.architecture.ui.page.BaseActivity;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.page.StateHolder;
import com.kunminx.architecture.ui.state.State;
import com.kunminx.puremusic.domain.event.Messages;
import com.kunminx.puremusic.domain.message.DrawerCoordinateManager;
import com.kunminx.puremusic.domain.message.PageMessenger;
import com.kunminx.puremusic.domain.proxy.PlayerManager;

/**
 * Create by KunMinX at 19/10/16
 */

public class MainActivity extends BaseActivity {
  private MainActivityStates mStates;
  private PageMessenger mMessenger;
  private boolean mIsListened = false;

  @Override
  protected void initViewModel() {
    mStates = getActivityScopeViewModel(MainActivityStates.class);
    mMessenger = getApplicationScopeViewModel(PageMessenger.class);
  }

  @Override
  protected DataBindingConfig getDataBindingConfig() {
    return new DataBindingConfig(R.layout.activity_main, BR.vm, mStates)
      .addBindingParam(BR.listener, new ListenerHandler());
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    PlayerManager.getInstance().init(this);
    mMessenger.output(this, messages -> {
      switch (messages.eventId) {
        case Messages.EVENT_CLOSE_ACTIVITY_IF_ALLOWED:
          NavController nav = Navigation.findNavController(this, R.id.main_fragment_host);
          if (nav.getCurrentDestination() != null && nav.getCurrentDestination().getId() != R.id.mainFragment) {
            nav.navigateUp();
          } else if (Boolean.TRUE.equals(mStates.isDrawerOpened.get())) {
            mStates.openDrawer.set(false);
          } else {
            super.onBackPressed();
          }
          break;
        case Messages.EVENT_OPEN_DRAWER:
          mStates.openDrawer.set(true);
          break;
      }
    });

    DrawerCoordinateManager.getInstance().isEnableSwipeDrawer().observe(this, aBoolean -> {
      mStates.allowDrawerOpen.set(aBoolean);
    });
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (!mIsListened) {
      mMessenger.input(new Messages(Messages.EVENT_ADD_SLIDE_LISTENER));
      mIsListened = true;
    }
  }

  @Override
  public void onBackPressed() {
    mMessenger.input(new Messages(Messages.EVENT_CLOSE_SLIDE_PANEL_IF_EXPANDED));
  }

  public class ListenerHandler extends DrawerLayout.SimpleDrawerListener {
    @Override
    public void onDrawerOpened(View drawerView) {
      super.onDrawerOpened(drawerView);
      mStates.isDrawerOpened.set(true);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
      super.onDrawerClosed(drawerView);
      mStates.isDrawerOpened.set(false);
      mStates.openDrawer.set(false);
    }
  }

  public static class MainActivityStates extends StateHolder {
    public final State<Boolean> isDrawerOpened = new State<>(false);
    public final State<Boolean> openDrawer = new State<>(false);
    public final State<Boolean> allowDrawerOpen = new State<>(true);
  }
}
