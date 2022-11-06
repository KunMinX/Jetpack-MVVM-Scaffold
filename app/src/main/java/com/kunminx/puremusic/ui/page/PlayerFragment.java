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

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.kunminx.architecture.ui.page.BaseFragment;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.page.StateHolder;
import com.kunminx.architecture.ui.state.State;
import com.kunminx.architecture.utils.ToastUtils;
import com.kunminx.architecture.utils.Utils;
import com.kunminx.player.PlayingInfoManager;
import com.kunminx.player.domain.PlayerEvent;
import com.kunminx.puremusic.BR;
import com.kunminx.puremusic.R;
import com.kunminx.puremusic.databinding.FragmentPlayerBinding;
import com.kunminx.puremusic.domain.event.Messages;
import com.kunminx.puremusic.domain.message.DrawerCoordinateManager;
import com.kunminx.puremusic.domain.message.PageMessenger;
import com.kunminx.puremusic.domain.proxy.PlayerManager;
import com.kunminx.puremusic.ui.page.helper.DefaultInterface;
import com.kunminx.puremusic.ui.view.PlayerSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.Objects;

/**
 * Create by KunMinX at 19/10/29
 */
public class PlayerFragment extends BaseFragment {
  private PlayerStates mStates;
  private PlayerSlideListener.SlideAnimatorStates mAnimatorStates;
  private PageMessenger mMessenger;
  private PlayerSlideListener mListener;

  @Override
  protected void initViewModel() {
    mStates = getFragmentScopeViewModel(PlayerStates.class);
    mAnimatorStates = getFragmentScopeViewModel(PlayerSlideListener.SlideAnimatorStates.class);
    mMessenger = getApplicationScopeViewModel(PageMessenger.class);
  }

  @Override
  protected DataBindingConfig getDataBindingConfig() {
    return new DataBindingConfig(R.layout.fragment_player, BR.vm, mStates)
      .addBindingParam(BR.panelVm, mAnimatorStates)
      .addBindingParam(BR.click, new ClickProxy())
      .addBindingParam(BR.listener, new ListenerHandler());
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mMessenger.output(this, messages -> {
      switch (messages.eventId) {
        case Messages.EVENT_ADD_SLIDE_LISTENER:
          if (view.getParent().getParent() instanceof SlidingUpPanelLayout) {
            SlidingUpPanelLayout sliding = (SlidingUpPanelLayout) view.getParent().getParent();
            mListener = new PlayerSlideListener((FragmentPlayerBinding) getBinding(), mAnimatorStates, sliding);
            sliding.addPanelSlideListener(mListener);
            sliding.addPanelSlideListener(new DefaultInterface.PanelSlideListener() {
              @Override
              public void onPanelStateChanged(
                View view, SlidingUpPanelLayout.PanelState panelState,
                SlidingUpPanelLayout.PanelState panelState1) {
                DrawerCoordinateManager.getInstance().requestToUpdateDrawerMode(
                  panelState1 == SlidingUpPanelLayout.PanelState.EXPANDED,
                  this.getClass().getSimpleName()
                );
              }
            });
          }
          break;
        case Messages.EVENT_CLOSE_SLIDE_PANEL_IF_EXPANDED:
          if (view.getParent().getParent() instanceof SlidingUpPanelLayout) {
            SlidingUpPanelLayout sliding = (SlidingUpPanelLayout) view.getParent().getParent();
            if (sliding.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
              sliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            } else {
              mMessenger.input(new Messages(Messages.EVENT_CLOSE_ACTIVITY_IF_ALLOWED));
            }
          } else {
            mMessenger.input(new Messages(Messages.EVENT_CLOSE_ACTIVITY_IF_ALLOWED));
          }
          break;
      }
    });

    PlayerManager.getInstance().getDispatcher().output(this, playerEvent -> {
      switch (playerEvent.eventId) {
        case PlayerEvent.EVENT_CHANGE_MUSIC:
          mStates.title.set(playerEvent.changeMusic.getTitle());
          mStates.artist.set(playerEvent.changeMusic.getSummary());
          mStates.coverImg.set(playerEvent.changeMusic.getImg());
          if (mListener != null) view.post(mListener::calculateTitleAndArtist);
          break;
        case PlayerEvent.EVENT_PROGRESS:
          mStates.maxSeekDuration.set(playerEvent.playingMusic.getDuration());
          mStates.currentSeekPosition.set(playerEvent.playingMusic.getPlayerPosition());
          break;
        case PlayerEvent.EVENT_PLAY_STATUS:
          mStates.isPlaying.set(!playerEvent.toPause);
          break;
        case PlayerEvent.EVENT_REPEAT_MODE:
          Enum<PlayingInfoManager.RepeatMode> mode = playerEvent.repeatMode;
          mStates.playModeIcon.set(PlayerManager.getInstance().getModeIcon(mode));
          break;
      }
    });
  }

  public class ClickProxy {
    public void playMode() {
      PlayerManager.getInstance().changeMode();
    }
    public void previous() {
      PlayerManager.getInstance().playPrevious();
    }
    public void togglePlay() {
      PlayerManager.getInstance().togglePlay();
    }
    public void next() {
      PlayerManager.getInstance().playNext();
    }
    public void showPlayList() {
      ToastUtils.showShortToast(getString(R.string.unfinished));
    }
    public void slideDown() {
      mMessenger.input(new Messages(Messages.EVENT_CLOSE_SLIDE_PANEL_IF_EXPANDED));
    }
    public void more() {
    }
  }

  public static class ListenerHandler implements DefaultInterface.OnSeekBarChangeListener {
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
      PlayerManager.getInstance().setSeek(seekBar.getProgress());
    }
  }

  public static class PlayerStates extends StateHolder {
    public final State<String> title = new State<>(Utils.getApp().getString(R.string.app_name));
    public final State<String> artist = new State<>(Utils.getApp().getString(R.string.app_name));
    public final State<String> coverImg = new State<>("");
    public final State<Drawable> placeHolder = new State<>(Objects.requireNonNull(ContextCompat.getDrawable(Utils.getApp(), R.drawable.bg_album_default)));
    public final State<Integer> maxSeekDuration = new State<>(0);
    public final State<Integer> currentSeekPosition = new State<>(0);
    public final State<Boolean> isPlaying = new State<>(false, true);
    public final State<MaterialDrawableBuilder.IconValue> playModeIcon = new State<>(PlayerManager.getInstance().getModeIcon());
  }
}
