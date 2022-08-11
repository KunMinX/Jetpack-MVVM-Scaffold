package com.kunminx.puremusic.domain.message;

import com.kunminx.architecture.domain.dispatch.MviDispatcher;
import com.kunminx.puremusic.domain.event.Messages;

/**
 * Create by KunMinX at 2022/7/4
 */
public class PageMessenger extends MviDispatcher<Messages> {
  @Override
  protected void onHandle(Messages event) {
    sendResult(event);
  }
}
